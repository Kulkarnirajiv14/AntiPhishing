package com.feature.urls;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.helper.ConnectionManager;
import com.helper.SpamModel;

public class URLFeaturesImpl implements IFeature<SpamModel, String> {

	@Override
	public SpamModel extractFeature(String Url) {
		// TODO Auto-generated method stub
		SpamModel sm = new SpamModel();
		// Url.is
		String protocol = new String();
		String host = new String();
		int port = 0;
		String Athority = new String();
		String Query = new String();
		String refrance = new String();

		try {
			URL myUrl = new URL(Url);

			protocol = myUrl.getProtocol();
			host = myUrl.getHost();
			port = myUrl.getPort();
			Athority = myUrl.getAuthority();
			refrance = myUrl.getRef();
			Query = myUrl.getQuery();
			System.out.println("protocol  " + protocol);
			System.out.println("host  " + host);
			System.out.println("port  " + port);
			System.out.println("Athority  " + Athority);
			System.out.println("refrance  " + refrance);
			System.out.println("Query  " + Query);
			if (host.indexOf("-") != -1) {
				sm.setDashInDomain(true);
			}
			if (port != -1) {
				sm.setNonStandardPort(true);
			}
			if (host.indexOf("@") != -1) {
				sm.setUrlAtTheRate(true);
			}
			if (host.indexOf("http") != -1) {
				sm.setStartsWithHttp(true);
			}
			
			if(Url.lastIndexOf("//")>6){
				sm.setFwdSlash(true);
			}

			sm.setWhitelisted(ConnectionManager.isWhitelisted(Url));
			sm.setSubDomainsCount(subDomainsCount(Url));
			sm.setiBlacklisted(ConnectionManager.isBlackListed(Url));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ip = getIpFromText(Url);
		if (ip.length() > 0) {
			sm.setIpAddressURLs(1);
		}
		boolean isShortened = isShortened(Url);
		if (isShortened) {
			sm.setShortenedURLS(1);
		}

		return sm;
	}
	
	
	

	public int subDomainsCount(String url) {
		URL u;
		int count = 0;
		try {
			u = new URL(url);
			System.out.println("u.getHost() " + u.getHost());
			int start = 0;

			String host = u.getHost();
			if (host.startsWith("www.")) {
				host = host.substring(4);
			}
			while (true) {
				start = host.indexOf(".", start+1);

				if (start == -1) {
					break;
				}
				count++;
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public boolean isShortened(String url) {
		// String url =
		// "https://stackoverflow.com/questions/2940858/kill-process-by-name";
		String s = getCompleteURL(url);
		System.out.println("Complete url " + s);

		String ss = url;
		if (url.length() < 50) {
			s = getCompleteURL(url);
		}

		if (!ss.equalsIgnoreCase(url)) {
			System.out.println("Shortened true");
			return true;
		}
		System.out.println("Shortened false");
		return false;
	}

	public static void main(String[] args) {
		String shortURL = "https://192.168.0.101:8080";
		IFeature<SpamModel, String> url = new URLFeaturesImpl();
		System.out.println("Short URL: " + url.extractFeature(shortURL));

	}

	public static String getCompleteURL(String shortURL) {
		// String shortURL = "http://goo.gl/j9KgUW";
		String completePath = shortURL;
		try {
			// System.out.println("Short URL: " + shortURL);
			URLConnection urlConn = connectURL(shortURL);
			urlConn.getHeaderFields();

			completePath = urlConn.getURL().toString();
			// System.out.println("Original URL: " + urlConn.getURL());
			List<String> locations = urlConn.getHeaderFields().get("Location");
			if (locations.size() > 0) {
				completePath = locations.get(0);
			}
			// https://www.youtube.com/watch?v=qMmhgCAAWVw

			/*
			 * connectURL - This function will take a valid url and return a URL
			 * object representing the url address.
			 */
			//
		} catch (Exception e) {
			// TODO: handle exception
		}
		return completePath;

	}

	public static URLConnection connectURL(String strURL) {
		URLConnection conn = null;
		try {
			URL inputURL = new URL(strURL);
			conn = inputURL.openConnection();
			int test = 0;

		} catch (MalformedURLException e) {
			System.out.println("Please input a valid URL");
		} catch (IOException ioe) {
			System.out.println("Can not connect to the URL");
		}
		return conn;
	}

	public String getIpFromText(String text) {
		String regularExpression = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
		String u = "";
		// String text=
		// "http://192.168.0.103/xpl/articleDetails.jsp?reload=true&tp=&arnumber=6409092&queryText%3Dphish+mail+guard";
		Pattern p = Pattern.compile(regularExpression);
		Matcher m = p.matcher(text);
		while (m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
				urlStr = urlStr.substring(1, urlStr.length() - 1);
			}

			u = urlStr;
			break;
		}
		System.out.println("ip address " + u);
		return u;
	}
}
