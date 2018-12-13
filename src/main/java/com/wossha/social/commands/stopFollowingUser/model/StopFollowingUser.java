package com.wossha.social.commands.stopFollowingUser.model;

public class StopFollowingUser {
	private String username;
	private String followingUserName;
	
	public StopFollowingUser(String username, String followingUserName) {
		this.username = username;
		this.followingUserName = followingUserName;
	}

	public StopFollowingUser() {}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFollowingUserName() {
		return followingUserName;
	}
	public void setFollowingUserName(String followingUserName) {
		this.followingUserName = followingUserName;
	}

	
	
}
