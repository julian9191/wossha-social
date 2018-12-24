package com.wossha.social.wsCommands.sendChatMessage.model;

import com.wossha.social.infrastructure.websocket.model.ChatMessage;

public class SendChatMessage {

	private String commandName;
	private ChatMessage message;
	
	public SendChatMessage(String commandName, ChatMessage message) {
		this.commandName = commandName;
		this.message = message;
	}

	public SendChatMessage() {}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public ChatMessage getMessage() {
		return message;
	}

	public void setMessage(ChatMessage message) {
		this.message = message;
	}

}
