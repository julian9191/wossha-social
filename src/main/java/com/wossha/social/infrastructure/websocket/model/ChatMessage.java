package com.wossha.social.infrastructure.websocket.model;

import java.util.Date;

public class ChatMessage implements IChatMessage{
	
	private String responseType = "CHAT-MESSAGE";
	private String fromId;
	private String toId;
	private String message;
	private Date sendOn;
	
	public ChatMessage(String fromId, String toId, String message, Date sendOn) {
		this.fromId = fromId;
		this.toId = toId;
		this.message = message;
		this.sendOn = sendOn;
	}

	public ChatMessage() {}


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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSendOn() {
		return sendOn;
	}

	public void setSendOn(Date sendOn) {
		this.sendOn = sendOn;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

}
