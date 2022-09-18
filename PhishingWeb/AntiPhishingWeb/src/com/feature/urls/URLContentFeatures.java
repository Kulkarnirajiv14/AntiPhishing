package com.feature.urls;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.helper.HttpsURLReader;

public class URLContentFeatures implements IFeature<URLContentModel, String> {
	public static void main(String[] args) {
		URLContentFeatures u = new URLContentFeatures();
		URLContentModel model = u.extractFeature("https://postalnfo.com/");
		System.out.println(model);
	}

	@Override
	public URLContentModel extractFeature(String url) {
		// TODO Auto-generated method stub
		URLContentModel content = new URLContentModel();

		try {
			URL u = new URL(url);
			System.out.println("u.getHost() " + u.getHost());
			int start = 0;

			String host = u.getHost();
			if (host.startsWith("www.")) {
				host = host.substring(4);
			}
			Document doc = null;
			if (url.toLowerCase().startsWith("http:")) {
				// doc = Jsoup.connect(url).get();

				try {
					doc = Jsoup.connect(url).get();
				} catch (Exception e) {
					// TODO: handle exception
					
					return null;
				}

			} else if (url.toLowerCase().startsWith("https:")) {
				String contentString = HttpsURLReader.readHttpsURL(url)
						.toString();
			
				doc = Jsoup.parse(contentString);

			} else {
				doc = Jsoup.connect(url).get();
			}
			try {
				Element e = doc.head().select("link[href~=.*\\.ico]").first();
				String favicoUrl = e.attr("href");
				if (favicoUrl.indexOf(host) != -1
						|| favicoUrl.indexOf(host) != -1
						|| !(favicoUrl.startsWith("http") || favicoUrl
								.startsWith("//"))) {
					content.setFaviconFromSameDomain(true);
				}
			} catch (Exception e) {
				content.setFaviconFromSameDomain(false);
			}

			content.setPerSameDomain(countURLDomains(url));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	public float countURLDomains(String url) {
		URL u;
		try {
			u = new URL(url);

			System.out.println("u.getHost() " + u.getHost());
			int start = 0;

			String host = u.getHost();
			if (host.startsWith("www.")) {
				host = host.substring(4);
			}
			System.out.println("host " + host);
			URLContentModel content = new URLContentModel();
			Document doc = null;
			if (url.toLowerCase().startsWith("http:")) {
				// doc = Jsoup.connect(url).get();

				doc = Jsoup.connect(url).get();

			} else if (url.toLowerCase().startsWith("https:")) {
				doc = Jsoup.parse(HttpsURLReader.readHttpsURL(url).toString());

			} else {
				doc = Jsoup.connect(url).get();
			}
			int totalLinks = 0;
			int successLinks = 0;
			Elements img = doc.getElementsByTag("img");
			for (Element el : img) {
				String src = el.attr("src");
				System.out.println("img " + el.toString());
				if (src != null) {

					if (src.indexOf(host) != -1
							|| !(src.startsWith("http") || src.startsWith("//"))) {

						// System.out.println("IN");
						successLinks++;
					}
					totalLinks++;
				}
			}
			Elements audios = doc.getElementsByTag("audio");
			for (Element el : audios) {
				String src = el.absUrl("src");
				if (src != null) {
					// System.out.println("audio "+src);
					if (src.indexOf(host) != -1
							|| !(src.startsWith("http") || src.startsWith("//"))) {
						successLinks++;
					}
					totalLinks++;
				}
			}
			Elements embeds = doc.getElementsByTag("embed");
			for (Element el : embeds) {
				String src = el.absUrl("src");
				if (src != null) {
					System.out.println("embed " + src);
					if (src.indexOf(host) != -1
							|| !(src.startsWith("http") || src.startsWith("//"))) {
						successLinks++;
					}
					totalLinks++;
				}
			}
			Elements i_frame = doc.getElementsByTag("i_frame");
			for (Element el : i_frame) {
				String src = el.absUrl("src");
				if (src != null) {
					System.out.println("i_frame " + src);
					if (src.indexOf(host) != -1
							|| !(src.startsWith("http") || src.startsWith("//"))) {
						successLinks++;
					}
					totalLinks++;
				}
			}
			System.out.println("successLinks " + successLinks + " totalLinks "
					+ totalLinks);
			
			float res = successLinks * 100f / totalLinks;

			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return 1;
	}
}
