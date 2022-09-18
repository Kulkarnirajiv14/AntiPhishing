package com.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import com.helper.StringHelper;

public class HttpClient {

	public static void main(String[] args) {
		//getHTTPSStream("https://www.bankofbaroda.co.in/");
		//StringBuffer sb=getHTTP_HTTPS_URLData("http://www.hdfcbank.com//assets/css/homepageBanners.css");
		StringBuffer sb=getHTTP_HTTPS_URLData("http://192.168.0.101/icici/Log%20in%20to%20Internet%20Banking_files/new_style1.css");
		
		//System.out.println("containt of css ::"+sb);
		// write a css into file 
		try{
		StringHelper.writeStringBufferToFile(sb,"D://work//project//antiPhishing//homepageBanners.css");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static StringBuffer getHTTP_HTTPS_URLData(String url) {
		StringBuffer CONTENT = null;
		try {
			InputStream stream = null;
			System.out.println("LOading CSS " + url);
			if (url.startsWith("http:")) {
				/* stream = new URL(filePath).openStream(); */
				// stream = new FileInputStream(new File(filePath));
				stream = HttpClient.getHTTPContentStream(url);
				// wrap as an InputSource

			} else if (url.startsWith("https:")) {
                  
				//System.out.println("**");
				stream = HttpClient.getHTTPSStream(url);
                
			} else {
				stream = new FileInputStream(new File(url));
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			while (true) {

				byte[] a = new byte[1024 * 4];
				int len = stream.read(a);
				if (len != -1) {
					baos.write(a, 0, len);

				} else {
					break;
				}
			}
			CONTENT = new StringBuffer(new String(baos.toByteArray()));
			//System.out.println("Containt :: "+CONTENT);
			baos.close();
			stream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return CONTENT;
	}

	public static String getHTTPContent(String url) throws Exception {

		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);

		in.close();

		return response.toString();
	}

	public static InputStream getHTTPContentStream(String url) {

		
		
		InputStream stream = null;
		URL website;
		try {
			website = new URL(url);
			URLConnection connection = (URLConnection)website.openConnection();
			//  HttpURLConnection connection = (HttpURLConnection) website.openConnection();
              // connection.connect();
			/*connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_7; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.205 Safari/534.16");
			connection.addRequestProperty("referer", "http://www.facebook.com");
	      	connection.connect();  */   

			  
			  System.out.println("*************");
			  System.out.println(connection.getInputStream());
			  System.out.println("*************");
			return connection.getInputStream();
			
		     //  stream=getContaintOFHttp(connection);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;

	}

	public static InputStream getHTTPSStream(String https_url) {

		/* https_url = "https://www.google.com/"; */
		/* https_url = "https://www.hdfcbank.com/"; */
		// https_url = "http://www.hdfcbank.com/";
		URL url;
		InputStream stream = null;
		try {
			System.out.println("https_url " + https_url);
			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.addRequestProperty("User-Agent", "Mozilla/4.76");
			  
			con.setRequestProperty("User-Agent", 
					   "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

			// dumpl all cert info
			// print_https_cert(con);

			// dump all the content
			stream=con.getInputStream();
			//stream = getContaint(con);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	public static InputStream getContaint(HttpsURLConnection con) {
		InputStream stream = null;
		if (con != null) {

			try {

				System.out.println("****** Content of the URL ********");
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				stream = con.getInputStream();
				String input;

				while ((input = br.readLine()) != null) {

					System.out.println(input);
					   //System.out.println("input size "+input.length());	  
				}
				br.close();
 
				
		    	   
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return stream;
	}
	public static InputStream getContaintOFHttp(URLConnection con) {
		InputStream stream = null;
		if (con != null) {

			try {

				System.out.println("****** Content of the URL ********");
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				stream = con.getInputStream();
				String input;

				while ((input = br.readLine()) != null) {

					System.out.println(input);

				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return stream;
	}

	public static void print_https_cert(HttpsURLConnection con) {

		if (con != null) {

			try {

				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for (Certificate cert : certs) {
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : "
							+ cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : "
							+ cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static InputStream readHttpsURLStream(String url) {

		return null;
	}

}
