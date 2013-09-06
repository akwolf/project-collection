package com.justsy.app.common.png.converter;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.CRC32;

import com.jcraft.jzlib.ZStream;

public class ConvertHandler {
	File m_file;
	protected ArrayList<PNGTrunk> trunks = null;

	// 文件转换
	public ConvertHandler(File file) {
		this.m_file = file;
		if (this.m_file.isDirectory()) {
			convertDirectory(this.m_file);
		} else if (isPNGFile(this.m_file)) {
			convertPNGFile(this.m_file);
		}
	}
	
	public ConvertHandler() {
	}

	protected boolean isPNGFile(File file) {
		String szFileName = file.getName();
		if (szFileName.length() < 5) {
			return false;
		}
		return szFileName.substring(szFileName.length() - 4).equalsIgnoreCase(".png");
	}

	protected PNGTrunk getTrunk(String szName) {
		if (this.trunks == null) {
			return null;
		}

		for (int n = 0; n < this.trunks.size(); n++) {
			PNGTrunk trunk = (PNGTrunk) this.trunks.get(n);
			if (trunk.getName().equalsIgnoreCase(szName)) {
				return trunk;
			}
		}
		return null;
	}

	protected ArrayList<PNGTrunk> getTrunkIDAT() {
		ArrayList<PNGTrunk> list = new ArrayList<PNGTrunk>();
		if (this.trunks == null) {
			return null;
		}
		for (int n = 0; n < this.trunks.size(); n++) {
			PNGTrunk trunk = (PNGTrunk) this.trunks.get(n);
			if (trunk.getName().equalsIgnoreCase("IDAT")) {
				list.add(trunk);
			}
		}
		return list;
	}
	
//	public 

	public File convertPNGFile(File pngFile) {
		String szFullPath = pngFile.getAbsolutePath();

		String newFileName = szFullPath.substring(0, szFullPath.lastIndexOf(File.separator) + 1)
				+ pngFile.getName().substring(0, pngFile.getName().lastIndexOf(".")) + "-new.png";

		try {
			DataInputStream file = new DataInputStream(new FileInputStream(pngFile));
			byte[] nPNGHeader = new byte[8];
			file.read(nPNGHeader);

//			boolean bWithCgBI = false;

			this.trunks = new ArrayList<PNGTrunk>();

			if ((nPNGHeader[0] == -119) && (nPNGHeader[1] == 80) && (nPNGHeader[2] == 78) && (nPNGHeader[3] == 71)
					&& (nPNGHeader[4] == 13) && (nPNGHeader[5] == 10) && (nPNGHeader[6] == 26) && (nPNGHeader[7] == 10)) {
				PNGTrunk trunk;
				do {
					trunk = PNGTrunk.generateTrunk(file);
					this.trunks.add(trunk);

					//					if (trunk.getName().equalsIgnoreCase("CgBI")) {
					//						bWithCgBI = true;
					//					}
				} while (!trunk.getName().equalsIgnoreCase("IEND"));
			}
			file.close();

			if (getTrunk("CgBI") != null) {
				//	          String szInfo = "Dir:" + pngFile.getAbsolutePath() + "--->" + newFileName;

				ArrayList<PNGTrunk> dataTrunkList = getTrunkIDAT();

				String dataTrunkName = dataTrunkList.get(0).getName();//m_szName
				int dataTrunkSzie = 0; //m_nSize

				int m_nDataLength = 0;
				int m_nCRCLength = 0;
				for (int i = 0; i < dataTrunkList.size(); i++) {
					dataTrunkSzie += dataTrunkList.get(i).getSize();
					m_nDataLength += dataTrunkList.get(i).getData().length;
					m_nCRCLength += dataTrunkList.get(i).getCRC().length;
				}
				byte[] m_nData = new byte[m_nDataLength]; //m_nData
				byte[] m_nCRC = new byte[m_nCRCLength]; //m_nCRC

				int tempdata = 0;
				int tempcrc = 0;
				for (int i = 0; i < dataTrunkList.size(); i++) {
					for (int j = 0; j < dataTrunkList.get(i).getData().length; j++) {
						m_nData[tempdata] = dataTrunkList.get(i).getData()[j];
						tempdata++;
					}
					for (int j = 0; j < dataTrunkList.get(i).getCRC().length; j++) {
						m_nCRC[tempcrc] = dataTrunkList.get(i).getCRC()[j];
						tempcrc++;
					}
				}

				PNGTrunk dataTrunk = new PNGTrunk(dataTrunkSzie, dataTrunkName, m_nData, m_nCRC);

				PNGIHDRTrunk ihdrTrunk = (PNGIHDRTrunk) getTrunk("IHDR");

				int nMaxInflateBuffer = 4 * (ihdrTrunk.m_nWidth + 1) * ihdrTrunk.m_nHeight;
				byte[] outputBuffer = new byte[nMaxInflateBuffer];

				ZStream inStream = new ZStream();
				inStream.avail_in = dataTrunk.getSize();
				inStream.next_in_index = 0;
				inStream.next_in = dataTrunk.getData();
				inStream.next_out_index = 0;
				inStream.next_out = outputBuffer;
				inStream.avail_out = outputBuffer.length;

				if (inStream.inflateInit(-15) != 0) {
					System.out.println("PNGCONV_ERR_ZLIB");
					return null;
				}

				int nResult = inStream.inflate(0);
				switch (nResult) {
				case 2:
					nResult = -3;
				case -4:
				case -3:
					inStream.inflateEnd();
					System.out.println("PNGCONV_ERR_ZLIB");
					return null;
				}

				nResult = inStream.inflateEnd();

				if (inStream.total_out > nMaxInflateBuffer) {
					System.out.println("PNGCONV_ERR_INFLATED_OVER");
				}

				int nIndex = 0;

				for (int y = 0; y < ihdrTrunk.m_nHeight; y++) {
					nIndex++;
					for (int x = 0; x < ihdrTrunk.m_nWidth; x++) {
						byte nTemp = outputBuffer[nIndex];
						outputBuffer[nIndex] = outputBuffer[(nIndex + 2)];
						outputBuffer[(nIndex + 2)] = nTemp;
						nIndex += 4;
					}
				}

				ZStream deStream = new ZStream();
				int nMaxDeflateBuffer = nMaxInflateBuffer + 1024;
				byte[] deBuffer = new byte[nMaxDeflateBuffer];

				deStream.avail_in = outputBuffer.length;
				deStream.next_in_index = 0;
				deStream.next_in = outputBuffer;
				deStream.next_out_index = 0;
				deStream.next_out = deBuffer;
				deStream.avail_out = deBuffer.length;
				deStream.deflateInit(9);
				nResult = deStream.deflate(4);

				if (deStream.total_out > nMaxDeflateBuffer) {
					System.out.println("PNGCONV_ERR_DEFLATED_OVER");
				}
				byte[] newDeBuffer = new byte[(int) deStream.total_out];
				for (int n = 0; n < deStream.total_out; n++) {
					newDeBuffer[n] = deBuffer[n];
				}
				CRC32 crc32 = new CRC32();
				crc32.update(dataTrunk.getName().getBytes());
				crc32.update(newDeBuffer);
				long lCRCValue = crc32.getValue();

				dataTrunk.m_nData = newDeBuffer;
				dataTrunk.m_nCRC[0] = (byte) (int) ((lCRCValue & 0xFF000000) >> 24);
				dataTrunk.m_nCRC[1] = (byte) (int) ((lCRCValue & 0xFF0000) >> 16);
				dataTrunk.m_nCRC[2] = (byte) (int) ((lCRCValue & 0xFF00) >> 8);
				dataTrunk.m_nCRC[3] = (byte) (int) (lCRCValue & 0xFF);
				dataTrunk.m_nSize = newDeBuffer.length;

				FileOutputStream outStream = new FileOutputStream(newFileName);
				byte[] pngHeader = { -119, 80, 78, 71, 13, 10, 26, 10 };
				outStream.write(pngHeader);
				boolean haveDone = false;
				for (int n = 0; n < this.trunks.size(); n++) {
					PNGTrunk trunk = (PNGTrunk) this.trunks.get(n);
					if (trunk.getName().equalsIgnoreCase("CgBI")) {
						continue;
					} else if (trunk.getName().equalsIgnoreCase("IDAT")) {
						if (!haveDone) {
							dataTrunk.writeToStream(outStream);
							haveDone = true;
						}
					} else {
						trunk.writeToStream(outStream);
					}

				}
				outStream.close();
			}
		} catch (IOException e) {
			System.out.println("Error --" + e.toString());
		}
//		return new File(newFileName);
		return new File(pngFile.getPath()) ;
	}

	private void convertDirectory(File dir) {
		File[] files = dir.listFiles();

		for (int n = 0; n < files.length; n++)
			if (files[n].isDirectory()) {
				convertDirectory(files[n]);
			} else if (isPNGFile(files[n]))
				convertPNGFile(files[n]);
	}
}