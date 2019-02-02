package com.wossha.social.commands.refuseFollow.model;

import com.wossha.social.infrastructure.enums.NotificationsEnum;

public class RefuseFollow {
	
	private String notificationType = NotificationsEnum.FOLLOW_REQUEST.name();;
	private String username;
	private String senderUsername;
	
	public RefuseFollow(String username, String senderUsername) {
		this.username = username;
		this.senderUsername = senderUsername;
	}

	public RefuseFollow() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public String getNotificationType() {
		return notificationType;
	}

}
