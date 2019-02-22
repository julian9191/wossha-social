package com.wossha.social.dto.post;

import java.sql.Timestamp;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Post {
	
	private Integer id;
	private String uuid;
	private String type;
	private String username;
	private String text;
	private String uuidParent;
	
	//Formats output date when this DTO is passed through JSON
	@JsonFormat(pattern = "yyyy-MM-dd")
	// Allows yyyy-MM-dd date to be passed into GET request in JSON
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp created;

	//Formats output date when this DTO is passed through JSON
	@JsonFormat(pattern = "yyyy-MM-dd")
	//Allows yyyy-MM-dd date to be passed into GET request in JSON
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp modified;

	public Post(Integer id, String uuid, String type, String username, String text, String uuidParent,
			Timestamp created, Timestamp modified) {
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.username = username;
		this.text = text;
		this.uuidParent = uuidParent;
		this.created = created;
		this.modified = modified;
	}
	
	public Post(String uuid) {
		this.uuid = uuid;
	}

	public Post() {}

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

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	
}
