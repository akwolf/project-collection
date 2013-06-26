package com.justsy.https;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

public class PostReadHttps {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SSLContext sslctx = SSLContext.getInstance("SSL");
		sslctx.init(null,
				new X509TrustManager[] { new Java2000TrustManager() }, null);

		HttpsURLConnection
				.setDefaultSSLSocketFactory(sslctx.getSocketFactory());
		URL url = new URL("https://localhost:8443/Test/HttpsServlet");
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		PrintStream ps = new PrintStream(con.getOutputStream());
		ps.println("f1=abc&f2=xyz");
		ps.close();
		con.connect();
		if (con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
		}
		con.disconnect();
	}

}
