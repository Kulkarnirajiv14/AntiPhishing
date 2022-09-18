package com.feature.urls;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;
import org.apache.commons.net.whois.WhoisClient;
import com.helper.DateHelper;

public class WebsiteAgeFeatureImpl implements IFeature<WhoisModel, String> {

	@Override
	public WhoisModel extractFeature(String url) {
		WhoisModel websiteWhoIs = new WhoisModel();
		String host = "";
		try {
			host = getDomainName(new URL(url));

			System.out.println(host);
			if (host.startsWith("www.")) {
				host = host.substring(4);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = crunchifyWhois(host);
		if (output.trim().length() == 0) {
			websiteWhoIs.setActive(false);
		} else {
			websiteWhoIs.setActive(true);
		}
		String creationDate = extractDate("Creation Date: ", output);
		System.out.println("CreationDate" + creationDate);
		String expiryDate = extractDate("Registry Expiry Date: ", output);
		String nameServers = extractDate("Name Server: ", output);
		if (creationDate.length() == 0) {
			websiteWhoIs.setActive(false);
			websiteWhoIs.setCreationDate(null);
			websiteWhoIs.setCreationDateDays(-1);
			websiteWhoIs.setExpirationDate(null);
			websiteWhoIs.setExpirationDateDays(-1);
		} else {

			Date creation = DateHelper.stringToDate(creationDate);
			Date expiry = DateHelper.stringToDate(expiryDate);

			System.out.println("\nTest Finished..");
			Date now = new Date();

			long difference = now.getTime() - creation.getTime();
			float daysBetween = (difference / (1000 * 60 * 60 * 24));

			long differenceExpiry = expiry.getTime() - now.getTime();
			float daysBetweenExpiry = (differenceExpiry / (1000 * 60 * 60 * 24));

			websiteWhoIs.setCreationDate(creation);
			websiteWhoIs.setCreationDateDays((int) daysBetween);
			websiteWhoIs.setExpirationDate(expiry);
			websiteWhoIs.setExpirationDateDays((int) daysBetweenExpiry);
		}

		websiteWhoIs.setNameServers(nameServers);
		System.out.println(websiteWhoIs);
		return websiteWhoIs;
	}

	public String getDomainName(URL url) {
		String strDomain;
		String[] strhost = url.getHost().split(Pattern.quote("."));
		String[] strTLD = { "com", "org", "net", "int", "edu", "gov", "mil",
				"arpa" };

		if (Arrays.asList(strTLD).indexOf(strhost[strhost.length - 1]) >= 0)
			strDomain = strhost[strhost.length - 2] + "."
					+ strhost[strhost.length - 1];
		else if (strhost.length > 2)
			strDomain = strhost[strhost.length - 3] + "."
					+ strhost[strhost.length - 2] + "."
					+ strhost[strhost.length - 1];
		else
			strDomain = strhost[strhost.length - 2] + "."
					+ strhost[strhost.length - 1];
		return strDomain;
	}

	private String extractDate(String toFind, String completeString) {

		int indexOf = completeString.indexOf(toFind);
		if (indexOf != -1) {
			int starts = indexOf + toFind.length();
			String creationDate = completeString.substring(starts,
					completeString.indexOf("\n", starts));

			return creationDate.trim();
		} else {
			return "";
		}
	}

	public static void main(String[] args) {

		IFeature<WhoisModel, String> obj = new WebsiteAgeFeatureImpl();
		// try {
		// System.out.println(new WebsiteAgeFeatureImpl().getDomainName(new
		// URL("https://en.wikipedia.org")));
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		System.out.println("Ranking : "
				+ obj.extractFeature("https://hosting.miarroba.com/nuevo.php"));

	}

	public String crunchifyWhois(String domainName) {

		StringBuilder whoisResult = new StringBuilder("");

		WhoisClient crunchifyWhois = new WhoisClient();
		try {
			// The WhoisClient class implements the client side of the Internet
			// Whois Protocol defined in RFC 954. To query a host you create a
			// WhoisClient instance, connect to the host, query the host, and
			// finally disconnect from the host. If the whois service you want
			// to query is on a non-standard port, connect to the host at that
			// port.
			crunchifyWhois.connect(WhoisClient.DEFAULT_HOST);

			String whoisData = crunchifyWhois.query("=" + domainName);
			System.out.println("whoisData " + whoisData);
			whoisResult.append(whoisData);
			crunchifyWhois.disconnect();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return whoisResult.toString();
	}
}
