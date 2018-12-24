package com.wossha.social.infrastructure.repositories;

import java.util.List;
import org.skife.jdbi.v2.IDBI;
import org.springframework.beans.factory.annotation.Autowired;
import com.wossha.social.commands.followUser.model.FollowUser;
import com.wossha.social.dto.FollowingUser;
import com.wossha.social.infrastructure.dao.follow.SocialDao;
import com.wossha.social.infrastructure.websocket.model.ChatMessage;

public class SocialRepository implements Repository<FollowUser> {

	@Autowired
	private IDBI dbi;

	private SocialDao socialDao;

	@Override
	public void add(FollowUser clothe) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.add(clothe);
	}

	public FollowUser getFollowersRelationship(String senderUsername, String receiverUsername) {
		socialDao = dbi.onDemand(SocialDao.class);
		return socialDao.getFollowersRelationship(senderUsername, receiverUsername);
	}
	
	public List<FollowingUser> getFollowingUsers(String username) {
		socialDao = dbi.onDemand(SocialDao.class);
		return socialDao.getFollowingUsers(username);
	}
	
	public List<ChatMessage> getChatMessageHistory(String username) {
		socialDao = dbi.onDemand(SocialDao.class);
		return socialDao.getChatMessageHistory(username);
	}
	
	public void stopFollowingUser(String username, String followingUserName) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.stopFollowingUser(username, followingUserName);
	}
	
	public void saveChatMessage(ChatMessage message) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.saveChatMessage(message);
	}
	
	@Override
	public void update(FollowUser clothe) {

	}

	@Override
	public void remove(FollowUser clothe) {

	}

}
