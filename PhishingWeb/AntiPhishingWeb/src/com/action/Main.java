package com.action;

import com.constant.ServerConstants;
import com.helper.PhishMailGuard;
import com.helper.SpamModel;
import com.helper.StringHelper;
import com.helper.WebsiteModel;

public class Main {
	
	
	public static void main(String[] args) {
		
		Execution("http://scratchpads.eu/sites/scratchpads.eu/files/css/css_BL3mHfWYiljhQ6gicOY18bJRHdPHtOiPcefzcmmXVHI.css");
	
	}
	public static void  Execution(String url)
	{
		// get a all css file and store in database 
/*		WebsiteModel siteToBeChecked=CrawlWebsite.createCSSTemplate(url,true);
		System.err.println("stored data in database");*/
		
		// get css file from database 
		
		  String urll="http://www.tmall.com"; 
          StringBuffer sb=HttpClient.getHTTP_HTTPS_URLData(urll);
		  // get info of url
          SpamModel sm= PhishMailGuard.getAllInformationFromUrl(urll); 
          String fileName =sm.getHost();
		  System.out.println("containt of css ::"+ sb);
		  // write a css into file 
		  try{
		   StringHelper.writeStringBufferToFile(sb,ServerConstants.PARSING_FILE+fileName+".css.txt");
		   }
		   catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	  //paring the css containt  
	/*	 CSSParserTest cp=new CSSParserTest();
		 if (cp.Parse(ServerConstants.PARSING_FILE+fileName+".css.txt",fileName)) {
            
			System.out.println("Parsing completed OK");

		  } else {
             
			System.out.println("Unable to parse CSS");

		}*/
		 
		//boolean result=CrawlWebsite.checkIfPhishing("https://www.bankofmaharashtra.in/"); 
		
		
		 
	}
	

}
