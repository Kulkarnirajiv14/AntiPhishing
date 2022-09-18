/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feature.urls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;


public class PhishingConnector {

	public static void main(String[] args) {
		//https://131187547-758969404492316567.preview.editmysite.com/uploads/1/3/1/1/131187547/bankofamerica2020-verification-center-boa-accounts.html
		checkOCR(
				Arrays.toString(new TestClassifier()
						.getURLFeatures("https://131187547-758969404492316567.preview.editmysite.com/uploads/1/3/1/1/131187547/bankofamerica2020-verification-center-boa-accounts.html")),
				"all");
	}

	public static String checkOCR(String path, String type) {
		String hostname = "localhost";
		int port = 7813;
		System.out.println("in check ocr... : " + path);
		// String path =
		// "D:\\work\\project\\PlantDiseaseDetection\\python-cnn\\training_data\\Tomato___Bacterial_spot\\b168.jpg";
		try (Socket socket = new Socket(hostname, port)) {

			OutputStream output = socket.getOutputStream();
			byte[] data = (path + "#" + type).getBytes();
			output.write(data);

			InputStream input = socket.getInputStream();
			StringBuffer sb = new StringBuffer();
			data = new byte[1024];
			// while(input.read()!=-1){
			int len = input.read(data);
			if (len != -1) {

				// }C
				System.out.println("this is: " + new String(data, 0, len));
				return (new String(data, 0, len));

			} else {
				return "unRecognized";
			}

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());
		}
		return null;
	}
}