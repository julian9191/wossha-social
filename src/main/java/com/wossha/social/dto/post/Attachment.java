package com.wossha.social.dto.post;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Attachment {
	
	private Integer id;
	private String uuid;
	private String type;
	private String uuidPost;
	private String url;
	// Formats output date when this DTO is passed through JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    // Allows yyyy-MM-dd date to be passed into GET request in JSON
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp created;
    // Formats output date when this DTO is passed through JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    // Allows yyyy-MM-dd date to be passed into GET request in JSON
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp modified;
    
	public Attachment(Integer id, String uuid, String type, String uuidPost, String url, Timestamp created,
			Timestamp modified) {
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.uuidPost = uuidPost;
		this.url = url;
		this.created = created;
		this.modified = modified;
	}

	public Attachment() {}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
