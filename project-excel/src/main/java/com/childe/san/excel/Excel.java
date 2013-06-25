package com.childe.san.excel;

import java.io.File;
import java.io.FileOutputStream;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Excel {

	public static void main(String[] args) throws Exception {

		FileOutputStream out = new FileOutputStream(new File("D:" + File.separator + "123.xls"));

		// 将创建excel写入内存
		WritableWorkbook book = Workbook.createWorkbook(out);
		WritableSheet sheet = book.createSheet("Justsy2012-01-12", 0);

		Label jobNum = new Label(0, 0, "工号");
		Label email = new Label(1, 0, "邮箱");
		Label step01 = new Label(2, 0, "步骤一");
		Label step02 = new Label(3, 0, "步骤二");
		Label step03 = new Label(4, 0, "步骤三");
		Label step04 = new Label(5, 0, "步骤四");
		Label step05 = new Label(6, 0, "步骤五");

		sheet.addCell(jobNum);
		sheet.addCell(email);
		sheet.addCell(step01);
		sheet.addCell(step02);
		sheet.addCell(step03);
		sheet.addCell(step04);
		sheet.addCell(step05);

		book.write();
		book.close();
		out.close();
		System.out.println("done.");
	}

}
