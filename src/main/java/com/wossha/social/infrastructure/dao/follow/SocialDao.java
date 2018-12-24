package com.wossha.social.infrastructure.dao.follow;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.springframework.stereotype.Repository;
import com.wossha.social.commands.followUser.model.FollowUser;
import com.wossha.social.dto.FollowingUser;
import com.wossha.social.infrastructure.websocket.model.ChatMessage;

@Repository
public abstract  class SocialDao {
	
	//SELECTS--------------------------------------------------------------------------------------------------------------------------------------
	
	@RegisterMapper(FollowUserMapperJdbi.class)
	@SqlQuery("select * from twss_followers where SENDER_USERNAME=:senderUsername AND RECEIVER_USERNAME=:receiverUsername")
	public abstract FollowUser getFollowersRelationship(@Bind("senderUsername") String senderUsername, @Bind("receiverUsername") String receiverUsername);
	
	@RegisterMapper(FollowingUserMapperJdbi.class)
	@SqlQuery("select " + 
			"RECEIVER_USERNAME as USERNAME, STATE " + 
			"from twss_followers where " + 
			"SENDER_USERNAME=:username")
	public abstract List<FollowingUser> getFollowingUsers(@Bind("username") String username);
	
	@RegisterMapper(ChatMessageMapperJdbi.class)
	@SqlQuery("SELECT * FROM TWSS_CHAT_MESSAGES WHERE SENDER_USERNAME = :username OR RECEIVER_USERNAME = :username")
	public abstract List<ChatMessage> getChatMessageHistory(@Bind("username") String username);

	
	// INSERTS--------------------------------------------------------------------------------------------------------------------------------------
	
	@SqlUpdate("Insert into TWSS_FOLLOWERS (UUID,SENDER_USERNAME,RECEIVER_USERNAME,STATE) values (:followUser.uuid, :followUser.senderUsername, :followUser.receiverUsername, :followUser.state)")
	public abstract void add(@BindBean("followUser") FollowUser followUser);
	
	@SqlUpdate("Insert into TWSS_CHAT_MESSAGES (SENDER_USERNAME,RECEIVER_USERNAME,MESSAGE,VIEWED) values (:message.fromId, :message.toId, :message.message, 1)")
	public abstract void saveChatMessage(@BindBean("message") ChatMessage message);
	

	// REMOVES--------------------------------------------------------------------------------------------------------------------------------------
	
	@SqlUpdate("DELETE FROM TWSS_CHAT_MESSAGES WHERE SENDER_USERNAME=:username AND RECEIVER_USERNAME=:followingUserName")
	public abstract void stopFollowingUser(@Bind("username") String username, @Bind("followingUserName") String followingUserName);
	
}