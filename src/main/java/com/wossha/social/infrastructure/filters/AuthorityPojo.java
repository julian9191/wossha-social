package com.wossha.social.infrastructure.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorityPojo {
	@JsonProperty
	private String key;
	@JsonProperty
	private String value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
