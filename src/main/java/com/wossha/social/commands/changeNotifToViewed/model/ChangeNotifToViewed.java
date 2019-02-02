package com.wossha.social.commands.changeNotifToViewed.model;

import java.util.List;

public class ChangeNotifToViewed {
	
	private List<String> ids;

	public ChangeNotifToViewed(List<String> ids) {
		this.ids = ids;
	}

	public ChangeNotifToViewed() {}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
