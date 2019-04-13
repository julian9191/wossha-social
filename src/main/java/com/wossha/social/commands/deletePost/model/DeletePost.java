package com.wossha.social.commands.deletePost.model;

import java.util.List;

import com.wossha.social.dto.post.Attachment;

public class DeletePost {

	private String commandName;
	private String username;
	private String uuidPost;
	private List<String> comments;
	private List<Attachment> attachments;

	public DeletePost(String commandName, String username, String uuidPost, List<Attachment> attachments,
			List<String> uuidImages) {
		this.commandName = commandName;
		this.username = username;
		this.uuidPost = uuidPost;
		this.comments = comments;
		this.attachments = attachments;
	}

	public DeletePost() {
	}

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

	public String getUuidPost() {
		return uuidPost;
	}

	public void setUuidPost(String uuidPost) {
		this.uuidPost = uuidPost;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

}
