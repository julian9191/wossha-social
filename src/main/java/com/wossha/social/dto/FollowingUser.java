package com.wossha.social.dto;

public class FollowingUser {
	
	private String username;
	private Integer state;
	
	public FollowingUser(String username, Integer state) {
		this.username = username;
		this.state = state;
	}

	public FollowingUser() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
