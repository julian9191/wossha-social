package com.wossha.social.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Notification {

	private Integer id;
	private String type;
	private String receiverUserName;
	private String senderUserName;
	private String senderName;
	private String senderPicture;
	private boolean viewed;
	private boolean opend;
	
	// Formats output date when this DTO is passed through JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    // Allows yyyy-MM-dd date to be passed into GET request in JSON
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date created;
	
	public Notification(Integer id, String type, String receiverUserName, String senderUserName, String senderName, String senderPicture,
			boolean viewed, boolean opend, Date created) {
		this.id = id;
		this.type = type;
		this.receiverUserName = receiverUserName;
		this.senderUserName = senderUserName;
		this.senderName = senderName;
		this.senderPicture = senderPicture;
		this.viewed = viewed;
		this.opend = opend;
		this.created = created;
	}
	
	public Notification(String type, String receiverUserName, String senderUserName, String senderName, String senderPicture,
			boolean viewed, boolean opend, Date created) {
		this.type = type;
		this.receiverUserName = receiverUserName;
		this.senderUserName = senderUserName;
		this.senderName = senderName;
		this.senderPicture = senderPicture;
		this.viewed = viewed;
		this.opend = opend;
		this.created = created;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	public boolean isOpend() {
		return opend;
	}

	public void setOpend(boolean opend) {
		this.opend = opend;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public String getSenderPicture() {
		return senderPicture;
	}

	public void setSenderPicture(String senderPicture) {
		this.senderPicture = senderPicture;
	}

}
