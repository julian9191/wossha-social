package com.wossha.social.infrastructure.websocket.model;

public class ConnectedUserMessage implements IChatMessage{
	
	private String responseType = "CONNECTED-USER-MESSAGE";
	private String username;
	
	public ConnectedUserMessage(String username) {
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
