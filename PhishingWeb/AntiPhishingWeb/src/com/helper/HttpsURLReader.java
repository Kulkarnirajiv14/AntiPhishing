package com.helper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsURLReader {
	public static void main(String[] args) throws Exception {
		
/*StringBuffer sv=readHttpsURL("https://retail.onlinesbi.com/personal/css/style.css");*/
		StringBuffer sv=readHttpsURL("https://postalnfo.com/");
	/*	StringBuffer sv=readHttpsURL("http://localhost:8080/BaitAlarms/pages/algorithm.jsp");*/
	
            System.out.println(sv);
            
	}

	public static StringBuffer readHttpsURL(String url1) {
		StringBuffer sb = new StringBuffer();
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			} };
			java.lang.System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");

			sc.init(null, trustAllCerts, new java.security.SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

			URL url = new URL(url1);
			URLConnection con = url.openConnection();
			
			con.setRequestProperty("Cookie", "foo=bar"); 
			con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0");
		
			Reader reader = new InputStreamReader(con.getInputStream());
			while (true) {
				int ch = reader.read();
				if (ch == -1) {
					break;
				}
				// System.out.print((char)ch);
				sb.append((char) ch);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;
	}
	public static InputStream readHttpsURLStream(String url1) {
			try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");

			sc.init(null, trustAllCerts, new java.security.SecureRandom());

			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

			URL url = new URL(url1);
			URLConnection con = url.openConnection();
			return con.getInputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}