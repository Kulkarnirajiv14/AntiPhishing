package com.feature.urls;

import com.google.common.base.MoreObjects;

public class URLContentModel {
	boolean faviconFromSameDomain = false;
	float perSameDomain = 100;

	public float getPerSameDomain() {
		return perSameDomain;
	}

	public void setPerSameDomain(float perSameDomain) {
		this.perSameDomain = perSameDomain;
	}

	public boolean isFaviconFromSameDomain() {
		return faviconFromSameDomain;
	}

	public void setFaviconFromSameDomain(boolean faviconFromSameDomain) {
		this.faviconFromSameDomain = faviconFromSameDomain;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("faviconFromSameDomain", faviconFromSameDomain)
				.add("perSameDomain", perSameDomain).toString();
	}
	
}
