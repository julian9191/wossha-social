package com.wossha.social.dto;

public class UserChatStatus {

	private String username;
	private Integer status;

	public UserChatStatus(String username, Integer status) {
		this.username = username;
		this.status = status;
	}

	public UserChatStatus() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
