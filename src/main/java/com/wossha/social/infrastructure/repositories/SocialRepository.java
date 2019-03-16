package com.wossha.social.infrastructure.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skife.jdbi.v2.IDBI;
import org.springframework.beans.factory.annotation.Autowired;

import com.wossha.msbase.models.Pagination;
import com.wossha.social.commands.followUser.model.FollowUser;
import com.wossha.social.dto.FollowingUser;
import com.wossha.social.dto.Notification;
import com.wossha.social.dto.post.Post;
import com.wossha.social.dto.post.Reaction;
import com.wossha.social.infrastructure.dao.follow.SocialDao;
import com.wossha.social.infrastructure.websocket.model.ChatMessage;

import java.util.ArrayList;
import java.util.Collections;

public class SocialRepository implements Repository<FollowUser> {

	@Autowired
	private IDBI dbi;

	private SocialDao socialDao;

	@Override
	public void add(FollowUser clothe) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.add(clothe);
	}
	
	public void addNotification(Notification notificacion) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.addNotification(notificacion);
	}
	
	public void addPost(Post post) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.addPost(post);
	}
	
	public void addReaction(Reaction reaction) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.addReaction(reaction);
	}

	public FollowUser getFollowersRelationship(String senderUsername, String receiverUsername) {
		socialDao = dbi.onDemand(SocialDao.class);
		return socialDao.getFollowersRelationship(senderUsername, receiverUsername);
	}
	
	public List<FollowingUser> getFollowingUsers(String username) {
		socialDao = dbi.onDemand(SocialDao.class);
		return socialDao.getFollowingUsers(username);
	}
	
	public Map<String, Object> getChatMessageHistory(String username, int init, int limit) {
		socialDao = dbi.onDemand(SocialDao.class);
		
		Integer count = socialDao.countChatMessageHistory(username);
		List<ChatMessage> history = socialDao.getChatMessageHistory(username, init, limit);
		Collections.reverse(history);
		
		Pagination pagination = new Pagination(count, init, limit);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("pagination", pagination);
		resultMap.put("result", history);
		return resultMap;
	}
	
	public Map<String, Object> getPosts(String username, int init, int limit, String profileUsername) {
		socialDao = dbi.onDemand(SocialDao.class);
		
		Integer count = 0;
		List<Post> history = new ArrayList<>();
		
		if(profileUsername!=null && !profileUsername.equals("")) {
			count = socialDao.countMyPosts(profileUsername);
			history = socialDao.getMyPosts(profileUsername, init, limit);
		}else {
			count = socialDao.countPosts(username);
			history = socialDao.getPosts(username, init, limit);
		}
		
		for (int i = 0; i < history.size(); i++) {
			history.get(i).setReactions(socialDao.getReactions(history.get(i).getUuid()));
		}
		
		Pagination pagination = new Pagination(count, init, limit);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("pagination", pagination);
		resultMap.put("result", history);
		return resultMap;
	}
	
	public Reaction getReactionByType(String username, String uuidPost, String reactionType) {
		socialDao = dbi.onDemand(SocialDao.class);
		return socialDao.getReactionByType(username, uuidPost, reactionType);
	}
	
	public Reaction getReaction(String username, String uuidPost) {
		socialDao = dbi.onDemand(SocialDao.class);
		return socialDao.getReaction(username, uuidPost);
	}
	
	public List<Notification> getUserNotifications(String username) {
		socialDao = dbi.onDemand(SocialDao.class);
		return socialDao.getUserNotifications(username);
	}
	
	public void stopFollowingUser(String username, String followingUserName) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.removeFollowingUser(username, followingUserName);
	}
	
	
	
	public void saveChatMessage(ChatMessage message) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.saveChatMessage(message);
	}
	
	public void changeStateFollowUser(String senderUsername, String username, int state) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.changeStateFollowUser(senderUsername, username, state);
	}
	
	public void updateReactionType(String username, String uuidPost, String reactionType) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.updateReactionType(username, uuidPost, reactionType);
	}
	
	public void deleteNotification(String senderUsername, String username, String notificationType) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.deleteNotification(senderUsername, username, notificationType);
	}
	
	public void removeReaction(String username, String uuidPost, String reactionType) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.removeReaction(username, uuidPost, reactionType);
	}
	
	public void changeNotifToViewed(String username, List<String> ids) {
		socialDao = dbi.onDemand(SocialDao.class);
		socialDao.changeNotifToViewed(dbi, username, ids);
	}
	
	@Override
	public void update(FollowUser clothe) {

	}

	@Override
	public void remove(FollowUser clothe) {

	}

	

}
