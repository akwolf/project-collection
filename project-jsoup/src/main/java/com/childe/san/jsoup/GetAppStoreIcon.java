package com.childe.san.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAppStoreIcon {
	// http://jsoup.org/cookbook/extracting-data/example-list-links
	public static void main(String[] args) throws IOException {
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "8182");
		Document doc = Jsoup.connect("http://itunes.apple.com/cn/app/id444934666").get();
		Element img = doc.select("#left-stack").select("img.artwork").first();
		System.out.println(img.html());
		Elements wrapper = doc.select("div.center-stack").select("img.portrait");
		String src = img.attr("abs:src");
		System.out.println("icon:" + src);
		for (Element element : wrapper) {
			System.out.println("desc:" + element.attr("abs:src"));
		}

		// =======================
		// Elements footer = doc.select("p.footer") ;
		// System.out.println(footer.size());
		// System.out.println("http://www.apple.com.cn/itunes/download/".equals(footer.absUrl("href")));

	}

}
