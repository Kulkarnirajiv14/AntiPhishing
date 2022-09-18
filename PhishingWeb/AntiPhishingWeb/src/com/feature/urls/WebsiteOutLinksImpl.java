package com.feature.urls;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteOutLinksImpl implements IFeature<Integer, String> {
	public static void main(String[] args) {

		IFeature<Integer, String> obj = new WebsiteOutLinksImpl();
		System.out.println("Ranking : " + obj.extractFeature("http://www.icicibank.com"));

	}
	@Override
	public Integer extractFeature(String url) {
		// TODO Auto-generated method stub
		int totalDomainContains = 0;
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			ArrayList<String> linksSet = new ArrayList<String>();
			for (int i = 0; i < links.size(); i++) {
				Element element = links.get(i);
				linksSet.add(new URL(element.baseUri()).getHost());
			}
			// int totalSize = linksSet.size();

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

		return totalDomainContains;
	}

}
