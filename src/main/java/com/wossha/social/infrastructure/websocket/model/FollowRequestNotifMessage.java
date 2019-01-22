package com.wossha.social.infrastructure.websocket.model;

import java.util.Date;

public class FollowRequestNotifMessage implements IChatMessage{
	
	private String responseType = "FOLLOW-REQUEST-NOTIF";
	private String username;
	
	public FollowRequestNotifMessage(String username) {
		this.username = username;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
