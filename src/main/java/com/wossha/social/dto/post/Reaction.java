package com.wossha.social.dto.post;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Reaction {

	private Integer id;
	private String uuid;
	private String type;
	private String uuidPost;
	private String username;
	// Formats output date when this DTO is passed through JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    // Allows yyyy-MM-dd date to be passed into GET request in JSON
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp created;
    
	public Reaction(Integer id, String uuid, String type, String uuidPost, String username, Timestamp created) {
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.uuidPost = uuidPost;
		this.username = username;
		this.created = created;
	}

	public Reaction() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUuidPost() {
		return uuidPost;
	}

	public void setUuidPost(String uuidPost) {
		this.uuidPost = uuidPost;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
    
	
    
}
