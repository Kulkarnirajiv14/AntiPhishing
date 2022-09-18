package com.helper;

import java.io.Serializable;
import java.util.ArrayList;

public class WebsiteModel implements Serializable {
	private static final long SerialVersionUID = 103;
	String websiteUrl="";
	String websiteHost="";
	String CopyRight="";
	public String getCopyRight() {
		return CopyRight;
	}
	public void setCopyRight(String copyRight) {
		CopyRight = copyRight;
	}
	public String getWebsiteHost() {
		return websiteHost;
	}
	public void setWebsiteHost(String websiteHost) {
		this.websiteHost = websiteHost;
	}
	ArrayList<String> cssURLList=new ArrayList<String>();
	
	public ArrayList<String> getCssURLList() {
		return cssURLList;
	}
	public void setCssURLList(ArrayList<String> cssURLList) {
		this.cssURLList = cssURLList;
	}
	ArrayList<CSSModel> cssList=new ArrayList<CSSModel>();
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	public ArrayList<CSSModel> getCssList() {
		return cssList;
	}
	public void setCssList(ArrayList<CSSModel> cssList) {
		this.cssList = cssList;
	}
	public int getWebsiteDivCount() {
		return websiteDivCount;
	}
	public void setWebsiteDivCount(int websiteDivCount) {
		this.websiteDivCount = websiteDivCount;
	}
	int websiteDivCount=0;
	String websiteTitle="";

	public String getWebsiteTitle() {
		return websiteTitle;
	}
	public void setWebsiteTitle(String websiteTitle) {
		this.websiteTitle = websiteTitle;
	}
	
}
