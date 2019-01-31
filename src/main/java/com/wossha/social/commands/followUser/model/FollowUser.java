package com.wossha.social.commands.followUser.model;

public class FollowUser {
	private String username;
	private String uuid;
	private String senderUsername;
	private String senderName;
	private String senderPicture;
	private String receiverUsername;
	private Integer state;

	public FollowUser(String username, String uuid, String senderUsername, String senderName, String senderPicture, String receiverUsername, Integer state) {
		this.username = username;
		this.uuid = uuid;
		this.senderUsername = senderUsername;
		this.senderName = senderName;
		this.receiverUsername = receiverUsername;
		this.state = state;
	}
	
	public FollowUser(String username, String uuid, String senderUsername, String receiverUsername, Integer state) {
		this.username = username;
		this.uuid = uuid;
		this.senderUsername = senderUsername;
		this.receiverUsername = receiverUsername;
		this.state = state;
	}

	public FollowUser() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderPicture() {
		return senderPicture;
	}

	public void setSenderPicture(String senderPicture) {
		this.senderPicture = senderPicture;
	}

}
