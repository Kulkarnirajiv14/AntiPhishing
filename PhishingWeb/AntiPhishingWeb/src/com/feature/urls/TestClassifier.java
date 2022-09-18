package com.feature.urls;

import java.util.Arrays;
import java.util.HashMap;

import com.feature.urls.IFeature;
import com.feature.urls.PageRankFeatureImpl;
import com.feature.urls.URLContentFeatures;
import com.feature.urls.URLContentModel;
import com.feature.urls.URLFeaturesImpl;
import com.feature.urls.WebsiteAgeFeatureImpl;
import com.feature.urls.WhoisModel;
import com.helper.ConnectionManager;
import com.helper.SpamModel;
import com.helper.StringHelper;
import com.helper.SvmClassifier;
//1. Having IP address
//2. URL Length
//3. URL Shortening service
//4. Having @ symbol
//5. Having double slash
//6. Having dash symbol(Prefix Suffix)
//7. Having multiple subdomains
//8. SSL Final State
//8. Domain Registration Length
//9. Favicon
//11. Request URL
//18. Age of Domain
//20. Web Traffic
//19. DNS Record

public class TestClassifier {

	// url =

	// PHISHING ->//
	// "https://131187547-758969404492316567.preview.editmysite.com/uploads/1/3/1/1/131187547/bankofamerica2020-verification-center-boa-accounts.html ";
	// url="https://pypi.org/project/whois/";
	// http://paypal-identity-form.com/
	// https://forms.gle/P7AKX7GZgjMFTWPe9 => PHishing
	//https://olx.pl.paytranc.pw ==> Phishing
	public static void main(String[] args) {
		String url = "https://docomo.ne.bvjp.xyz";
		// double isPhishing = checkURLPhishing(url);
		checkURLPhishing(url);
		//PhishingConnector.checkOCR(url, "");
	}

	public static double[] getURLFeatures(String url) {
		// String url = "http://192.168.0.101/t";
		TestClassifier.isPhishing =-1;
		// url =
		// "https://131187547-758969404492316567.preview.editmysite.com/uploads/1/3/1/1/131187547/bankofamerica2020-verification-center-boa-accounts.html ";
		// url="https://pypi.org/project/whois/";
		IFeature<SpamModel, String> urlInfo = new URLFeaturesImpl();
		SpamModel spam = urlInfo.extractFeature(url);
		if (spam == null) {

			double[] ret = new double[3];
			System.out.println("LENGHT: " + ret.length);
			return ret;
		}
		IFeature<WhoisModel, String> whois = new WebsiteAgeFeatureImpl();
		WhoisModel whoisModel = whois.extractFeature(url);

		IFeature<URLContentModel, String> urlContent = new URLContentFeatures();
		URLContentModel urlContentAttributes = urlContent.extractFeature(url);
		if (urlContentAttributes == null
				|| whoisModel.getCreationDate() == null) {
			TestClassifier.isPhishing = 1;
		}
		System.out.println("Ranking : " + whoisModel);
		IFeature<Integer, String> obj = new PageRankFeatureImpl();
		int pageRanking = obj.extractFeature(url);
		System.out.println("Ranking : " + pageRanking);
		System.out.println("Short URL: " + spam);
		double[] data = new double[13];
		data = new double[] { 1, -1, -1, 1, 1, 1, -1, 1, 1, 1, 0, 1, 1 };

		// 1st param IP Address in URL
		if (spam.ipAddressURLs > 0) {
			data[0] = -1;
		} else {
			data[0] = 1;
		}

		// 2nd param in URL
		if (url.length() < 54) {
			data[1] = 1;
		} else if (url.length() >= 54 && url.length() <= 75) {
			data[1] = 0;
		} else {
			data[1] = -1;
		}

		// 3rd parameter in url
		if (spam.shortenedURLS > 0) {
			data[2] = -1;
		} else {
			data[2] = 1;
		}

		// 4th parameter in @
		if (url.indexOf("@") != -1) {
			data[3] = -1;
		} else {
			data[3] = 1;
		}

		// 5th parameter double_slash_redirecting
		if (spam.isFwdSlash()) {
			data[4] = -1;
		} else {
			data[4] = 1;
		}

		// 6th parameter prefix_suffix
		if (spam.isDashInDomain()) {
			data[5] = -1;
		} else {
			data[5] = 1;
		}

		// 7th parameter having_sub_domain
		if (spam.getSubDomainsCount() <= 2) {
			data[6] = 1;
		} else if (spam.getSubDomainsCount() == 3) {
			data[6] = 0;
		} else {
			data[6] = -1;
		}
//		data[6] = -1;
		// 8th parameter domain_registeration_length
		if (!whoisModel.isActive()) {
			data[7] = -1;
		} else {
			if (whoisModel.getExpirationDateDays() < 365) {
				data[7] = -1;
			} else {
				data[7] = 1;
			}
		}
		System.out.println("urlContentAttributes " + urlContentAttributes);
		// 9th parameter faviconFromSameDomain
		if (urlContentAttributes != null
				&& urlContentAttributes.isFaviconFromSameDomain()) {
			data[8] = 1;
		} else {
			data[8] = -1;
		}
		// 10th parameter requestURLFromSameDomain
		if (urlContentAttributes.getPerSameDomain() < 22) {
			data[9] = 1;
		} else if (urlContentAttributes.getPerSameDomain() >= 22
				&& urlContentAttributes.getPerSameDomain() <= 61) {
			data[9] = 0;
		} else {
			data[9] = -1;
		}
		System.out.println("request_url tc" + data[9]);
		// 11th parameter age_of_domain
		if (!whoisModel.isActive()) {
			data[10] = -1;
		} else {
			if (whoisModel.getCreationDateDays() == -1
					|| (whoisModel.getCreationDateDays() + whoisModel
							.getExpirationDateDays()) < 180) {
				data[10] = 0;
			} else {
				data[10] = 1;
			}
		}
		// 12th parameter page ranking
//		if (pageRanking > 0 && pageRanking < 100000) {
//			data[11] = 1;	// suspicious
//		} else if (pageRanking > 100000 ){
//			data[11] = -1;	// phishing
//		}
		
		if (pageRanking > 0 && pageRanking < 100000) {
			data[11] = -1;	// suspicious
		} else if (pageRanking > 100000 ){
			data[11] = 1;	// phishing
		}

		// 13th parameter dns record
		if (whoisModel.isActive()) {
			data[12] = 1;
		} else {
			data[12] = -1;
		}

		System.out.println(Arrays.toString(data));
		return data;
	}

	public static boolean checkURLPhishing(String url) {
		double[] data = null;   
		boolean isPhishing = false;
		double d2 = 0;
		try {
			data = getURLFeatures(url);
			System.out.println("data: " + data);
			if (data.length < 5 || TestClassifier.isPhishing == 1) {
				d2 = 0;
				System.out.println("data  Check: " );
			} else {
				 data = new double[] { 1, -1, -1, -1,1, -1, -1, 1,
				 -1.0, -1.0, -1.0, -1.0, -1.0 };
//				 d2 = SvmClassifier.getSVMPredication(data);
				d2 = StringHelper.n2i(PhishingConnector.checkOCR(Arrays.toString(data), ""));

			}
			System.out.println("Prediction Result: " + d2);
			if (d2 == 0) { // output is -1
				System.out.println("PHISHING");
				isPhishing = true;
			} else {
				System.out.println("NORMAL");
				isPhishing = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			d2 = 0;
			System.out.println("PHISHING");
			isPhishing = true;
		}
		System.out.println(Arrays.toString(data));

		// target variable with two levels(1, -1), i.e. whether a website is a
		// phishing website or not.
		// double d=SvmClassifier.getSVMPredication(new
		// double[]{1,0,-1,1,1,-1,1,1});
		// System.out.println(d);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("d2", StringHelper.n2s(d2));
		map.put("arr", Arrays.toString(data));
		return isPhishing;
	}

	public static int isPhishing = -1;
}
