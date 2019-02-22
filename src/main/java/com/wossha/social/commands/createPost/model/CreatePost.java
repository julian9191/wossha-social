package com.wossha.social.commands.createPost.model;

public class CreatePost {

	private String commandName;
	private String username;
	private String text;
	
	public CreatePost(String commandName, String username, String text) {
		this.commandName = commandName;
		this.username = username;
		this.text = text;
	}

	public CreatePost() {}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
