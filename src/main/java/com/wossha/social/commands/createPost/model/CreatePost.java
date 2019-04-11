package com.wossha.social.commands.createPost.model;

import java.util.List;

import com.wossha.msbase.models.PictureFileDTO;

public class CreatePost {

	private String commandName;
	private String username;
	private String uuidParent;
	private String text;
	private List<PictureFileDTO> images;
	private String videoCode;
	
	public CreatePost(String commandName, String username, String uuidParent, String text) {
		this.commandName = commandName;
		this.username = username;
		this.uuidParent = uuidParent;
		this.text = text;
	}

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

	public String getUuidParent() {
		return uuidParent;
	}

	public void setUuidParent(String uuidParent) {
		this.uuidParent = uuidParent;
	}

	public List<PictureFileDTO> getImages() {
		return images;
	}

	public void setImages(List<PictureFileDTO> images) {
		this.images = images;
	}

	public String getVideoCode() {
		return videoCode;
	}

	public void setVideoCode(String videoCode) {
		this.videoCode = videoCode;
	}
	
}
