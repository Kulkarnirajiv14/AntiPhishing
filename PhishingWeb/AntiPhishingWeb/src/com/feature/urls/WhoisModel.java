package com.feature.urls;

import java.util.Date;

import com.google.common.base.MoreObjects;

public class WhoisModel {
	boolean isActive=true;
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	String nameServers;
	Date creationDate;
	Date expirationDate;
	int creationDateDays;
	int expirationDateDays;

	public String getNameServers() {
		return nameServers;
	}

	public void setNameServers(String nameServers) {
		this.nameServers = nameServers;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getCreationDateDays() {
		return creationDateDays;
	}

	public void setCreationDateDays(int creationDateDays) {
		this.creationDateDays = creationDateDays;
	}

	public int getExpirationDateDays() {
		return expirationDateDays;
	}

	public void setExpirationDateDays(int expirationDateDays) {
		this.expirationDateDays = expirationDateDays;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("nameServers", nameServers)
				.add("creationDate", creationDate)
				.add("expirationDate", expirationDate)
				.add("creationDateDays", creationDateDays)
				.add("expirationDateDays", expirationDateDays).toString();
	}

}
