package com.feature.urls;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PageRankFeatureImpl implements IFeature<Integer, String> {

	@Override
	public Integer extractFeature(String url) {
		return getAlexaRanking(url);
	}

	public static void main(String[] args) {
//http://dinas.tomsk.ru/err/?www.paypal.ch/ch/cgi-bin/webscr1.htm?cmd=_login-run&dispatch=5885d80a13c0db1f1ff80d546411d7f8a8350c132bc41e0934cfc023d4r4ere32132
		IFeature<Integer, String> obj = new PageRankFeatureImpl();
		System.out.println("Ranking : " + obj.extractFeature("http://paypal-identity-form.com/"));

	}

	public int getAlexaRanking(String domain) {

		int result = -1;

		String url = "http://data.alexa.com/data?cli=10&url=" + domain;

		try {
			// https://awis.api.alexa.com/api?Action=TrafficHistory&Range=5&ResponseGroup=History&Url=sfgate.com
			URLConnection conn = new URL(url).openConnection();
			InputStream is = conn.getInputStream();

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			System.out.println(doc.toString());
			Element element = doc.getDocumentElement();

			NodeList nodeList = element.getElementsByTagName("REACH");
			if (nodeList.getLength() > 0) {
				Element elementAttribute = (Element) nodeList.item(0);
				String ranking = elementAttribute.getAttribute("RANK");
				if (!"".equals(ranking)) {
					result = Integer.valueOf(ranking);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;
	}
}
