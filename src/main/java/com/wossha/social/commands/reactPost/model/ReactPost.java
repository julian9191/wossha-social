package com.wossha.social.commands.reactPost.model;

public class ReactPost {

	private String commandName;
	private String username;
	private String reactionType;
	private String uuidPost;

	public ReactPost(String commandName, String username, String reactionType, String uuidPost) {
		this.commandName = commandName;
		this.username = username;
		this.reactionType = reactionType;
		this.uuidPost = uuidPost;
	}

	public ReactPost() {}

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

	public String getReactionType() {
		return reactionType;
	}

	public void setReactionType(String reactionType) {
		this.reactionType = reactionType;
	}

	public String getUuidPost() {
		return uuidPost;
	}

	public void setUuidPost(String uuidPost) {
		this.uuidPost = uuidPost;
	}
}
