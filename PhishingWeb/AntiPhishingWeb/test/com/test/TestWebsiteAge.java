package com.test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestWebsiteAge {
	public static void main(String[] args) {
		// IFeature<WebsiteModel, String> obj = new WebsiteAgeFeatureImpl();
		// float daysBetween = obj.extractFeature("google.com");
		// System.out.println(daysBetween);
		// String url =
		// "http://192.168.0.101/icici/Log%20in%20to%20Internet%20Banking.htm";
		String url = "https://serverfault.com";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			ArrayList<String> linksSet = new ArrayList<String>();
			for (int i = 0; i < links.size(); i++) {
				Element element = links.get(i);
				linksSet.add(new URL(element.baseUri()).getHost());
			}
			// int totalSize = linksSet.size();
			int totalDomainContains = 0;
			for (Iterator iterator = linksSet.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				if (url.indexOf(string) != -1) {
					totalDomainContains++;
				}
			}
			System.out.println("linksSet " + linksSet);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
