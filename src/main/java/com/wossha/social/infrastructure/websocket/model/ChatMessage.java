package com.wossha.social.infrastructure.websocket.model;

import java.util.Date;

public class ChatMessage {
	
	private Integer fromId;
	private Integer toId;
	private String message;
	
	public ChatMessage(Integer fromId, Integer toId, String message) {
		this.fromId = fromId;
		this.toId = toId;
		this.message = message;
	}

	public ChatMessage() {}


	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
