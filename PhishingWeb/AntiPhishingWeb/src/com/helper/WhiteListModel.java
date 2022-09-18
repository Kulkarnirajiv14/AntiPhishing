package com.helper;

import java.io.Serializable;

public class WhiteListModel implements Serializable {
	private static final long SerialVersionUID = 106;
	String phish_id, url, phish_detail_url, submission_time, verified,
			verification_time, online, target;

	public String getPhish_id() {
		return phish_id;
	}

	public void setPhish_id(String phish_id) {
		this.phish_id = phish_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhish_detail_url() {
		return phish_detail_url;
	}

	public void setPhish_detail_url(String phish_detail_url) {
		this.phish_detail_url = phish_detail_url;
	}

	public String getSubmission_time() {
		return submission_time;
	}

	public void setSubmission_time(String submission_time) {
		this.submission_time = submission_time;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public String getVerification_time() {
		return verification_time;
	}

	public void setVerification_time(String verification_time) {
		this.verification_time = verification_time;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	String urlId, urldesc, whitelist;

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public String getUrldesc() {
		return urldesc;
	}

	public void setUrldesc(String urldesc) {
		this.urldesc = urldesc;
	}

	public String getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(String whitelist) {
		this.whitelist = whitelist;
	}

}
