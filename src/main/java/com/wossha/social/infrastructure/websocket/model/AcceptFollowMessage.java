package com.wossha.social.infrastructure.websocket.model;

import com.wossha.social.dto.Notification;

public class AcceptFollowMessage implements IChatMessage{

	private String responseType = "ACCEPT-FOLLOW";
	private String fromId;
	private String toId;
	private Notification message;
	
	public AcceptFollowMessage(String fromId, String toId, Notification message) {
		this.fromId = fromId;
		this.toId = toId;
		this.message = message;
	}

	public AcceptFollowMessage() {}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public Notification getMessage() {
		return message;
	}

	public void setMessage(Notification message) {
		this.message = message;
	}
	
}
