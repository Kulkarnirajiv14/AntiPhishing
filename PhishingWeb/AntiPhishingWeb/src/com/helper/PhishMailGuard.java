package com.helper;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*import com.action.GoogleSafeBrowsing;*/
import com.constant.ServerConstants;

public class PhishMailGuard {
	public static HashMap map = new HashMap();
	public static HashMap dbmap = new HashMap();
	static {
//		map = DBUtils
//				.getQueryMap("SELECT urldesc, whitelist FROM whitelisturl");
//		File file = new File(ServerConstants.SPAM_DIR + "whitelisturl.bin");
//		ObjectFileHelper.writeObject2File(map, file.getAbsolutePath());
//		dbmap = DBUtils
//				.getQueryMap("SELECT urldesc, whitelist FROM whitelisturl");
//		com.mysql.jdbc.Class.releaseHeapMemory();
	}

	/*
	 * public static boolean isWhitelist(String url){
	 * if(url==null||url.length()==0){ return true; }
	 * System.out.println("URL "+url); String s=""; try {
	 * if(url.toLowerCase().indexOf("mailto:")!=-1){ return true; }
	 * System.out.println("MAP is "+map); URL u=new URL(url);
	 * s=u.getProtocol()+"://"+u.getHost();
	 * 
	 * if(map.get(s)!=null){ boolean s1=StringHelper.n2b(map.get(s)); return s1;
	 * } } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * int i= GoogleSafeBrowsing.isPhishing(s); if(i==1||i==2||i==3){ map.put(s,
	 * "false"); return false; } map.put(s, "true");
	 * 
	 * return true; }
	 */
	public static void updateWhitelistURL() {
		if (map.size() > 0) {
			Set set = map.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = StringHelper.n2s(iterator.next());
				if (key.length() > 0) {
					boolean val = StringHelper.n2b(map.get(key));
					if (dbmap.get(key) == null) {
						String query = "insert into whitelisturl (urldesc, whitelist) values(?,?)";
						DBUtils.executeUpdate(query, new Object[] { key,
								val + "" });
					}
				}

			}
		}
	}

	public static boolean isEncoded(String url) {
		boolean flag = false;
		try {
			if (url.equals(java.net.URLDecoder.decode(url, "UTF-8"))) {
				System.out.println("URL didn't contain encoded parts.");
				flag = false;
			} else {
				System.out.println("URL contained encoded parts.");
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 * public static void main1212(String[] args) { Message[] arr =
	 * ReadRecentMail.getAllGmails( ReadRecentMail.ACCOUNT_USER2,
	 * ReadRecentMail.ACCOUNT_PASSWORD2);
	 * 
	 * System.out.println(arr.length); for (int i =0; i < arr.length; i++) {
	 * Message msg = arr[i];
	 * 
	 * 
	 * try {
	 * 
	 * String subject = msg.getSubject();
	 * 
	 * System.out.println(subject); ArrayList<String> data =
	 * ReadRecentMail.processMessageBody(msg); System.out.println("Data " +
	 * data.size()); // FileWriter a=new FileWriter(new File("D:/a.html")); //
	 * a.write(data.get(0)); // a.close(); for (int j = 0; j < data.size(); j++)
	 * { String d=data.get(j); if(d.startsWith("HTML_")){ d=d.substring(5);
	 * if(subject.indexOf("Pro Tip: Track Changes to Your Sheet")!=-1){
	 * System.out.println("GOt Message hEre "); processMessage(d,msg); } //
	 * processMessage(d,msg); }
	 * 
	 * }
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * } }
	 */
	/*
	 * public static String getMessageBodyHtml(Message msg){
	 * 
	 * ArrayList<String> data = ReadRecentMail.processMessageBody(msg);
	 * System.out.println("Data " + data.size()); // FileWriter a=new
	 * FileWriter(new File("D:/a.html")); // a.write(data.get(0)); // a.close();
	 * String d=""; for (int j = 0; j < data.size(); j++) { d=data.get(j);
	 * if(d.startsWith("HTML_")){ d=d.substring(5); break; }
	 * 
	 * } return d; }
	 */
	/*
	 * public static SpamModel getMessageBody(Message msg){ SpamModel sm=new
	 * SpamModel(); ArrayList<String> data =
	 * ReadRecentMail.processMessageBody(msg); System.out.println("Data " +
	 * data.size()); // FileWriter a=new FileWriter(new File("D:/a.html")); //
	 * a.write(data.get(0)); // a.close(); for (int j = 0; j < data.size(); j++)
	 * { String d=data.get(j); if(d.startsWith("HTML_")){ d=d.substring(5);
	 * sm=processMessage(d,msg); }
	 * 
	 * } return sm; }
	 */
	public static String getIpFromText(String text) {
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

	public static void main(String[] args) {
		String shortURL = "https://www.yahoo.com";
		System.out.println("Short URL: " + getAllInformationFromUrl(shortURL));

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

	public static String getURL(String text) {
		// processMessage("");
		// String text =
		// "download_recording true Package available www.abc.com/recDownload/635131586215948750.exe";
		// String regex =
		// "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";

		if (text.indexOf("@") != -1 && text.lastIndexOf(".") != -1
				&& text.lastIndexOf(".") > text.indexOf("@")) {
			return "";
		}
		String regularExpression = "((((ht{2}ps?://)?)((w{3}\\.)?))?)[^.&&[a-zA-Z0-9]][a-zA-Z0-9.-]+[^.&&[a-zA-Z0-9]](\\.[a-zA-Z]{2,3})";

		String u = "";
		Pattern p = Pattern.compile(regularExpression);
		Matcher m = p.matcher(text);
		while (m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
				urlStr = urlStr.substring(1, urlStr.length() - 1);
			}
			System.out.println(urlStr);
			u = urlStr;
			break;
		}

		// String regularExpression =
		// "((((ht{2}ps?://)?)((w{3}\\.)?))?)[^.&&[a-zA-Z0-9]][a-zA-Z0-9.-]+[^.&&[a-zA-Z0-9]](\\.[a-zA-Z]{2,3})";

		// System.out.println("www.google.com".matches(regularExpression));
		// System.out.println("www.google.co.uk".matches(regularExpression));
		// System.out.println("http://www.google.com".matches(regularExpression));
		// System.out.println("http://www.google.co.uk".matches(regularExpression));
		// System.out.println("https://www.google.com".matches(regularExpression));
		// System.out.println("https://www.google.co.uk".matches(regularExpression));
		// System.out.println("google.com".matches(regularExpression));
		// System.out.println("google.co.uk".matches(regularExpression));
		// System.out.println("google.mu".matches(regularExpression));
		// System.out.println("mes.intnet.mu".matches(regularExpression));
		// System.out.println("cse.uom.ac.mu".matches(regularExpression));

		// //cannot contain 2 '.' after www
		// assertFalse("www..dr.google".matches(regularExpression));
		//
		// //cannot contain 2 '.' just before com
		// assertFalse("www.dr.google..com".matches(regularExpression));
		//
		// // to test case where url www must be followed with a '.'
		// assertFalse("www:google.com".matches(regularExpression));
		//
		// // to test case where url www must be followed with a '.'
		// //assertFalse("http://wwwe.google.com".matches(regularExpression));
		//
		// // to test case where www must be preceded with a '.'
		// assertFalse("https://www@.google.com".matches(regularExpression));
		//
		return u;
	}

	public void performDNSTest(Elements links) {
		for (Element link : links) {
			String s = link.text();
			System.out
					.println(link.attr("abs:href") + " [" + link.text() + "]");

		}

	}

	/*
	 * public static SpamModel processMessage(String html,String from,String
	 * toString,String subject) { SpamModel sm=new SpamModel(); boolean
	 * multipleTo=false; int ipAddressURLs=0; int shortenedURLS=0; int
	 * anchorHrefURLDiffs=0; int whitelistURLs=0; boolean inputTypes=false;
	 * boolean hasSpamKeywords=false; // String[]
	 * spamKeywords={"Urgent Action","COngratulation you have won",""};
	 * 
	 * // StringBuffer sv = StringHelper.readFileContent("D:\\a.html"); Document
	 * doc = Jsoup.parse(html.toString()); Elements links =
	 * doc.select("a[href]"); Elements iframes = doc.getElementsByTag("iframe");
	 * Elements inps = doc.select("input"); Elements inps_hiddenn =
	 * doc.select("input[type=hidden]"); int no=0;
	 * if(inps!=null&&inps_hiddenn!=null){ no=inps.size()-inps_hiddenn.size(); }
	 * 
	 * if(no>0){ inputTypes=true; }
	 * 
	 * for (Element link : links) {
	 * 
	 * System.out.println(link.attr("abs:href") + " [" + link.text() + "]");
	 * String href=link.attr("abs:href");
	 * 
	 * 
	 * 
	 * String anchor_text=link.text();
	 * 
	 * // Check if url has ip address String ip=getIpFromText(href);
	 * if(ip.length()>0){ System.out.println("Got IP ADDRESS "+href+" => "+ip);
	 * ipAddressURLs++; } //System.out.println(" URLs "+text); String
	 * anchorTextURL=getURL(anchor_text); //
	 * System.out.println(" URLs o/p "+anchorTextURL); // Achor Text and URL
	 * Diffrence Test
	 * if(anchorTextURL.length()>0&&!anchorTextURL.equalsIgnoreCase("ASP.net")){
	 * String ip1="",ip2=""; String link1="",link2=""; try {
	 * 
	 * if(href.toLowerCase().startsWith("http")){ // check if url is absolute or
	 * relative int first_index=href.indexOf("://")+3; int
	 * last_index=href.indexOf("/",first_index); if(last_index!=-1)
	 * link1=href.substring(first_index,last_index); else
	 * link1=href.substring(first_index); }else{ link1=href; }
	 * 
	 * if(anchorTextURL.toLowerCase().startsWith("http")){ int
	 * first_index=anchorTextURL.indexOf("://")+3; int
	 * last_index=anchorTextURL.indexOf("/",first_index); if(last_index!=-1)
	 * link2=anchorTextURL.substring(first_index,last_index); else
	 * link2=anchorTextURL.substring(first_index); }else{ link2=anchorTextURL; }
	 * if(link1.equalsIgnoreCase(link2)&&link2.length()>0){ ip1=ip2=""; }else{
	 * ip1=java.net.InetAddress.getByName(link1).getHostAddress();
	 * ip2=java.net.InetAddress.getByName(link2).getHostAddress(); } } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * if(!ip1.equalsIgnoreCase(ip2)&&(ip1.length()>0)&&(ip2.length()>0)){
	 * System
	 * .out.println("Got ANchor HREF Diff IP1 IP2 "+ip1+" "+ip2+" "+link1+" "
	 * +link2); anchorHrefURLDiffs++; } } String s=href; if(href.length()<50){
	 * s=getCompleteURL(href); } boolean b=false; if(!s.equalsIgnoreCase(href)){
	 * 
	 * b=isWhitelist(s); if(!b){
	 * System.out.println("Got Shortened URL  IP1 IP2 "+s+" "+href);
	 * shortenedURLS++; } }else{ b=isWhitelist(href); }
	 * 
	 * if(!b){ System.out.println("Got BLACKLISTED URL "+href); whitelistURLs++;
	 * }
	 * 
	 * 
	 * }
	 * 
	 * System.out.println(links.size()+"===>"+
	 * ipAddressURLs+" "+shortenedURLS+" "
	 * +anchorHrefURLDiffs+" "+whitelistURLs+" "+inputTypes); boolean
	 * co=(ipAddressURLs
	 * >0||shortenedURLS>0||anchorHrefURLDiffs>0||shortenedURLS>
	 * 0||inputTypes||(hasSpamKeywords&&multipleTo)); if(co){
	 * System.out.println(
	 * "+++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>   PHISHING EMAIL  "
	 * +subject); }
	 * 
	 * sm.setAnchorHrefURLDiffs(anchorHrefURLDiffs);
	 * sm.setHasSpamKeywords(hasSpamKeywords); sm.setInputTypes(inputTypes);
	 * sm.setIpAddressURLs(ipAddressURLs); sm.setMultipleTo(multipleTo);
	 * sm.setShortenedURLS(shortenedURLS); sm.setWhitelistURLs(whitelistURLs);
	 * return sm; }
	 */

	public static void main12(String[] args) {

		// String
		// Url="https:///www.bing.com/search?q=css+code+example&PC=U316&FORM=CHROMN";
		SpamModel sm = getAllInformationFromUrl("https://196.102.1.102/www.bing.com/search?q=css+code+example&PC=U316&FORM=CHROMN/");
		System.out.println(" ip Address count : " + sm.getIpAddressURLs());
		System.out.println(" protocol : " + sm.getProtocol());
		System.out.println(" host : " + sm.getHost());
		System.out.println(" port : " + sm.getPort());
		System.out.println(" Athority : " + sm.getAthority());
		System.out.println(" Query : " + sm.getQuery());
		System.out.println(" Refrance : " + sm.getRefrance());

		/*
		 * String query=sm.getQuery(); String [] parameter=query.split("&");
		 * for(String par:parameter) { System.out.println("  => "+par); }
		 */
		InetAddress[] HostIP = getHostIP(sm.getHost());

		for (InetAddress printHost : HostIP) {
			System.out.println(" host list of site : " + printHost);
		}
		boolean result = checkWhitelistUrl(sm.getProtocol() + "://"
				+ sm.getHost());
		System.out.println("result url is white list or not :" + result);

		updateWhitelistURL();

	}

	public static void main1(String[] args) {

		int shortenedURLS = 0;
		String url = "https://stackoverflow.com/questions/2940858/kill-process-by-name";
		String s = getCompleteURL(url);
		System.out.println("Complete url " + s);

		String ss = url;
		if (url.length() < 50) {
			s = getCompleteURL(url);
		}

		if (!ss.equalsIgnoreCase(url)) {
			shortenedURLS++;
		}
		System.out.println("shortenedURLS " + shortenedURLS);

	}

	public static SpamModel getAllInformationFromUrl(String Url) {
		SpamModel sm = new SpamModel();
		boolean multipleTo = false;
		int ipAddressURLs = 0;

		// Url.is
		String protocol = new String();
		String host = new String();
		int port = 0;
		String Athority = new String();
		String Query = new String();
		String refrance = new String();

		// Check if url has ip address
		String href = Url;
		String anchor_text = Url;

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

			sm.setWhitelisted(ConnectionManager.isWhitelisted(Url));

			sm.setiBlacklisted(ConnectionManager.isBlackListed(Url));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ip = getIpFromText(href);
		if (ip.length() > 0) {
			System.out.println("Got IP ADDRESS " + href + " => " + ip);
			ipAddressURLs++;
		}
		// boolean whiteList = isWhitelist(Url);
		// System.out.println(" whiteList " + whiteList);

		// boolean sortListUrl=is
		// System.out.println(" URLs "+text);
		String anchorTextURL = getURL(anchor_text);
		// System.out.println(" URLs o/p "+anchorTextURL);
		// Achor Text and URL Diffrence Test
		// if (anchorTextURL.length() > 0
		// && !anchorTextURL.equalsIgnoreCase("ASP.net")) {
		// String ip1 = "", ip2 = "";
		// String link1 = "", link2 = "";
		// try {
		//
		// if (href.toLowerCase().startsWith("http")) { // check if url is
		// // absolute or
		// // relative
		// int first_index = href.indexOf("://") + 3;
		// int last_index = href.indexOf("/", first_index);
		// if (last_index != -1)
		// link1 = href.substring(first_index, last_index);
		// else
		// link1 = href.substring(first_index);
		// } else {
		// link1 = href;
		// }
		//
		// if (anchorTextURL.toLowerCase().startsWith("http")) {
		// int first_index = anchorTextURL.indexOf("://") + 3;
		// int last_index = anchorTextURL.indexOf("/", first_index);
		// if (last_index != -1)
		// link2 = anchorTextURL
		// .substring(first_index, last_index);
		// else
		// link2 = anchorTextURL.substring(first_index);
		// } else {
		// link2 = anchorTextURL;
		// }
		// if (link1.equalsIgnoreCase(link2) && link2.length() > 0) {
		// ip1 = ip2 = "";
		// } else {
		// ip1 = java.net.InetAddress.getByName(link1)
		// .getHostAddress();
		// ip2 = java.net.InetAddress.getByName(link2)
		// .getHostAddress();
		// }
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// if (!ip1.equalsIgnoreCase(ip2) && (ip1.length() > 0)
		// && (ip2.length() > 0)) {
		// System.out.println("Got ANchor HREF Diff IP1 IP2 " + ip1 + " "
		// + ip2 + " " + link1 + " " + link2);
		// anchorHrefURLDiffs++;
		// }
		// }
		int shortenedURLS = 0;
		String s = href;
		// if (href.length() < 50) {
		// s = getCompleteURL(href);
		// if (s.length() > 15 && href.length() > 15) {
		//
		// String s1 = s.substring(0, 15);
		// String s2 = href.substring(0, 15);
		//
		// if (!s1.equalsIgnoreCase(s2)) {
		// shortenedURLS = 1;
		// }
		//
		// }
		// }

		boolean b = false;
		// if (!s.equalsIgnoreCase(href)) {
		//
		// b = isWhitelist(s);
		// if (!b) {
		// System.out.println("Got Shortened URL  IP1 IP2 " + s + " "
		// + href);
		// shortenedURLS++;
		// }
		// } else {
		// b = isWhitelist(href);
		// }
		//
		// if (!b) {
		// System.out.println("Got BLACKLISTED URL " + href);
		// whitelistURLs++;
		// }

		// System.out.println(links.size()+"===>"+
		// ipAddressURLs+" "+shortenedURLS+" "+anchorHrefURLDiffs+" "+whitelistURLs+" "+inputTypes);
		// boolean co = (ipAddressURLs > 0 || shortenedURLS > 0
		// || anchorHrefURLDiffs > 0 || shortenedURLS > 0 || inputTypes ||
		// (hasSpamKeywords && multipleTo));
		// if (co) {
		// System.out
		// .println("+++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>   PHISHING EMAIL  ");
		// }

		// sm.setAnchorHrefURLDiffs(anchorHrefURLDiffs);
		// sm.setHasSpamKeywords(hasSpamKeywords);
		// sm.setInputTypes(inputTypes);
		sm.setIpAddressURLs(ipAddressURLs);
		sm.setProtocol(protocol);
		sm.setHost(host);
		sm.setPort(port);
		sm.setAthority(Athority);
		sm.setQuery(Query);
		sm.setRefrance(refrance);

		sm.setMultipleTo(multipleTo);
		sm.setShortenedURLS(shortenedURLS);
		if (sm.isWhitelisted()) {
			sm.setHasSpamKeywords(false);
		} else {
			if (sm.getShortenedURLS() > 0 || sm.isDashInDomain()
					|| sm.isStartsWithHttp() || sm.isNonStandardPort()
					|| sm.isUrlAtTheRate() || sm.isiBlacklisted()) {
				sm.setHasSpamKeywords(true);
			}
		}
		System.out.println("   IS FAKE " + sm.isHasSpamKeywords());
		// sm.setWhitelistURLs(whitelistURLs);
		return sm;
	}

	public static InetAddress[] getHostIP(String url) {
		InetAddress[] myHost = {};
		try {
			/*
			 * InetAddress[] myHost =
			 * InetAddress.getAllByName("www.google.com");
			 */
			myHost = InetAddress.getAllByName(url);
			return myHost;
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}

		return myHost;
	}

	public static boolean checkWhitelistUrl(String host) {

		boolean res = ConnectionManager.isBlackListed(host);
		if (res == true) {
			return true;
		}

		return false;
	}

	public static HashMap spamKeywords = new HashMap();
//	static {
//		File file = new File(ServerConstants.SPAM_DIR + "spam.bin");
//		if (!file.exists())
//			spamKeywords = ConnectionManager
//					.getQueryMap("SELECT spamWord,1 as val FROM spamkeyword ");
//		else {
//			System.out.println("Reading " + file.getAbsolutePath());
//			spamKeywords = (HashMap) ObjectFileHelper.readObjectFromFile(file
//					.getAbsolutePath());
//		}
//		ObjectFileHelper.writeObject2File(spamKeywords, file.getAbsolutePath());
//	}

	public static boolean hasSpamKeyword(String subject, String body) {
		boolean hasSpamKeywords = false;
		Set words = spamKeywords.keySet();
		for (Iterator iterator = words.iterator(); iterator.hasNext();) {
			String word = (String) iterator.next();
			word = word.toLowerCase();
			body = body.toLowerCase();
			int occurancesSubject1 = subject.length()
					- subject.replace(word, "").length();

			int occurancesBody1 = body.length()
					- body.replace(word, "").length();
			int occurancesSubject = 0;
			int occurancesBody = 0;
			if (occurancesSubject1 > 0) {
				occurancesSubject = occurancesSubject1 / word.length();
			}
			if (occurancesBody1 > 0) {
				occurancesBody = occurancesBody1 / word.length();
			}
			if (occurancesSubject >= 1 || occurancesBody >= 1) {
				hasSpamKeywords = true;
				System.out.println("Got SPAM Keyword " + word);
				System.out
						.println("================================================BODY ====================");
				System.out.println(subject);
				// System.out.println(body);
				System.out
						.println("================================================BODY ====================");
			}

		}

		return hasSpamKeywords;
	}

}
