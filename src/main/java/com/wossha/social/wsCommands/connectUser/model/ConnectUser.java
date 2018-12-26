package com.wossha.social.wsCommands.connectUser.model;

import com.wossha.social.infrastructure.websocket.model.ChatMessage2;

public class ConnectUser {
	private String commandName;
	private ChatMessage2 message;
	
	public ConnectUser(String commandName, ChatMessage2 message) {
		this.commandName = commandName;
		this.message = message;
	}
	
	public ConnectUser() {}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public ChatMessage2 getMessage() {
		return message;
	}

	public void setMessage(ChatMessage2 message) {
		this.message = message;
	}
	
}
