package com.wossha.social.infrastructure.dao.follow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.Update;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.springframework.stereotype.Repository;
import com.wossha.social.infrastructure.dao.BaseDao;
import com.wossha.social.commands.followUser.model.FollowUser;
import com.wossha.social.dto.FollowingUser;
import com.wossha.social.dto.Notification;
import com.wossha.social.dto.post.Attachment;
import com.wossha.social.dto.post.Post;
import com.wossha.social.dto.post.Reaction;
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
	@SqlQuery("SELECT * FROM TWSS_CHAT_MESSAGES WHERE SENDER_USERNAME = :username OR RECEIVER_USERNAME = :username ORDER BY CREATED DESC OFFSET :init ROWS FETCH NEXT :limit ROWS ONLY")
	public abstract List<ChatMessage> getChatMessageHistory(@Bind("username") String username, @Bind("init") int init, @Bind("limit") int limit);

	@SqlQuery("SELECT count(*) FROM TWSS_CHAT_MESSAGES WHERE SENDER_USERNAME = :username OR RECEIVER_USERNAME = :username")
	public abstract Integer countChatMessageHistory(@Bind("username") String username);
	
	@RegisterMapper(NotificationMapperJdbi.class)
	@SqlQuery("select * from TWSS_NOTIFICATIONS WHERE RECEIVER_USERNAME = :username")
	public abstract List<Notification> getUserNotifications(@Bind("username") String username);

	@SqlQuery("SELECT count(*) FROM TWSS_POSTS WHERE USERNAME=:username AND UUID_PARENT is null")
	public abstract Integer countMyPosts(@Bind("username") String username);
	
	@RegisterMapper(PostMapperJdbi.class)
	@SqlQuery("SELECT * FROM TWSS_POSTS WHERE USERNAME=:username AND UUID_PARENT is null ORDER BY CREATED DESC OFFSET :init ROWS FETCH NEXT :limit ROWS ONLY")
	public abstract List<Post> getMyPosts(@Bind("username") String username, @Bind("init") int init, @Bind("limit") int limit);
	
	@SqlQuery("SELECT count(*) FROM TWSS_POSTS WHERE UUID_PARENT is null")
	public abstract Integer countPosts(@Bind("username") String username);
	
	@RegisterMapper(PostMapperJdbi.class)
	@SqlQuery("SELECT * FROM TWSS_POSTS WHERE UUID_PARENT is null ORDER BY CREATED DESC OFFSET :init ROWS FETCH NEXT :limit ROWS ONLY")
	public abstract List<Post> getPosts(@Bind("username") String username, @Bind("init") int init, @Bind("limit") int limit);
	
	@RegisterMapper(ReactionMapperJdbi.class)
	@SqlQuery("SELECT * FROM TWSS_REACTIONS WHERE USERNAME=:username AND UUID_POST=:uuidPost AND TYPE=:reactionType")
	public abstract Reaction getReactionByType(@Bind("username") String username, @Bind("uuidPost") String uuidPost, @Bind("reactionType") String reactionType);
	
	@RegisterMapper(ReactionMapperJdbi.class)
	@SqlQuery("SELECT * FROM TWSS_REACTIONS WHERE USERNAME=:username AND UUID_POST=:uuidPost")
	public abstract Reaction getReaction(@Bind("username") String username, @Bind("uuidPost") String uuidPost);
	
	@RegisterMapper(ReactionMapperJdbi.class)
	@SqlQuery("SELECT * FROM TWSS_REACTIONS WHERE UUID_POST=:uuidPost")
	public abstract List<Reaction> getReactions(@Bind("uuidPost") String uuidPost);
	
	public List<Reaction> getReactionsByGroup(IDBI dbi, List<String> postUuids) {

		if(postUuids.isEmpty()) {
			return new ArrayList<Reaction>();
		}
		
		BaseDao<Reaction> baseDao = new BaseDao<>();
		String query = "SELECT * FROM TWSS_REACTIONS R ";
		query += "WHERE R.UUID_POST IN (<postUuids>) ";
		

		Map<String, List<String>> typesBindMap = new HashMap<>();
		typesBindMap.put("postUuids", postUuids);
		query = baseDao.generateBingIdentifier(query, typesBindMap);

		Handle h = dbi.open();
		@SuppressWarnings("unchecked")
		Query<Reaction> q = h.createQuery(query).map(new ReactionMapperJdbi());

		q = baseDao.addInClauseBind(q, typesBindMap);
		List<Reaction> output = (List<Reaction>) q.list();

		return output;
	}
	
	public List<Attachment> getAttachmentsByGroup(IDBI dbi, List<String> postUuids) {

		if(postUuids.isEmpty()) {
			return new ArrayList<Attachment>();
		}
		
		BaseDao<Attachment> baseDao = new BaseDao<>();
		String query = "SELECT * FROM TWSS_ATTACHMENTS R ";
		query += "WHERE R.UUID_POST IN (<postUuids>) ";
		

		Map<String, List<String>> typesBindMap = new HashMap<>();
		typesBindMap.put("postUuids", postUuids);
		query = baseDao.generateBingIdentifier(query, typesBindMap);

		Handle h = dbi.open();
		@SuppressWarnings("unchecked")
		Query<Attachment> q = h.createQuery(query).map(new AttachmentMapperJdbi());

		q = baseDao.addInClauseBind(q, typesBindMap);
		List<Attachment> output = (List<Attachment>) q.list();

		return output;
	}
	
	public List<Post> getCommentsByGroup(IDBI dbi, List<String> postUuids) {

		if(postUuids.isEmpty()) {
			return new ArrayList<Post>();
		}
		
		BaseDao<Post> baseDao = new BaseDao<>();
		String query = "SELECT * FROM TWSS_POSTS P ";
		query += "WHERE P.UUID_PARENT IN (<postUuids>) ";
		

		Map<String, List<String>> typesBindMap = new HashMap<>();
		typesBindMap.put("postUuids", postUuids);
		query = baseDao.generateBingIdentifier(query, typesBindMap);

		Handle h = dbi.open();
		@SuppressWarnings("unchecked")
		Query<Post> q = h.createQuery(query).map(new PostMapperJdbi());

		q = baseDao.addInClauseBind(q, typesBindMap);
		List<Post> output = (List<Post>) q.list();

		return output;
	}
	
	public List<String> getOwnPosts(IDBI dbi, List<String> uuidPosts, String username) {
	
		BaseDao<String> baseDao = new BaseDao<>();
		String query = "SELECT UUID FROM TWSS_POSTS P ";
		query += "WHERE P.UUID IN (<uuidPosts>) AND USERNAME = :username ";
		

		Map<String, List<String>> typesBindMap = new HashMap<>();
		typesBindMap.put("uuidPosts", uuidPosts);
		query = baseDao.generateBingIdentifier(query, typesBindMap);

		Handle h = dbi.open();
		@SuppressWarnings("unchecked")
		Query<String> q = h.createQuery(query).mapTo(String.class)
		.bind("username", username);

		q = baseDao.addInClauseBind(q, typesBindMap);
		List<String> output = (List<String>) q.list();

		return output;
	}

	
	// INSERTS--------------------------------------------------------------------------------------------------------------------------------------
	
	@SqlUpdate("Insert into TWSS_FOLLOWERS (UUID,SENDER_USERNAME,RECEIVER_USERNAME,STATE) values (:followUser.uuid, :followUser.senderUsername, :followUser.receiverUsername, :followUser.state)")
	public abstract void add(@BindBean("followUser") FollowUser followUser);
	
	@SqlUpdate("Insert into TWSS_CHAT_MESSAGES (SENDER_USERNAME,RECEIVER_USERNAME,MESSAGE,VIEWED) values (:message.fromId, :message.toId, :message.message, 1)")
	public abstract void saveChatMessage(@BindBean("message") ChatMessage message);
	
	@SqlUpdate("Insert into TWSS_NOTIFICATIONS (TYPE,RECEIVER_USERNAME,SENDER_USERNAME,SENDER_NAME,SENDER_PICTURE,VIEWED,OPENED) values (:nt.type,:nt.receiverUserName,:nt.senderUserName,:nt.senderName,:nt.senderPicture,:nt.viewed,:nt.opend)")
	public abstract void addNotification(@BindBean("nt") Notification notificacion);
	
	@SqlUpdate("Insert into TWSS_POSTS (UUID,TYPE,USERNAME,TEXT,UUID_PARENT) values (:post.uuid, :post.type, :post.username, :post.text, :post.uuidParent)")
	public abstract void addPost(@BindBean("post") Post post);
	
	@SqlUpdate("Insert into TWSS_REACTIONS (UUID,TYPE,UUID_POST,USERNAME) values (:reaction.uuid, :reaction.type, :reaction.uuidPost, :reaction.username)")
	public abstract void addReaction(@BindBean("reaction") Reaction reaction);
	
	@SqlUpdate("Insert into TWSS_ATTACHMENTS (UUID,TYPE,UUID_POST,URL,USERNAME) values (:attachment.uuid, :attachment.type, :attachment.uuidPost, :attachment.url, :attachment.username)")
	public abstract void addAttachments(@BindBean("attachment") Attachment attachment);
	
	// UPDATES--------------------------------------------------------------------------------------------------------------------------------------

	@SqlUpdate("UPDATE TWSS_FOLLOWERS SET STATE = :state WHERE SENDER_USERNAME=:senderUsername AND RECEIVER_USERNAME=:username")
	public abstract void changeStateFollowUser(@Bind("senderUsername") String senderUsername, @Bind("username") String username, @Bind("state") int state);
	
	@SqlUpdate("UPDATE TWSS_REACTIONS SET TYPE = :reactionType WHERE USERNAME=:username AND UUID_POST=:uuidPost")
	public abstract void updateReactionType(@Bind("username") String username, @Bind("uuidPost") String uuidPost, @Bind("reactionType") String reactionType);
	
	public void changeNotifToViewed(IDBI dbi, String username, List<String> ids) {

		BaseDao baseDao = new BaseDao<>();
		String query = "UPDATE TWSS_NOTIFICATIONS ";
		query += "SET VIEWED = 1 ";
		query += "WHERE RECEIVER_USERNAME = :username ";
		query += !ids.isEmpty() ? "AND ID IN (<ids>) " : " ";

		Map<String, List<String>> typesBindMap = new HashMap<>();
		typesBindMap.put("ids", ids);
		query = baseDao.generateBingIdentifier(query, typesBindMap);

		Handle h = dbi.open();	
		Update q = h.createStatement(query)
		.bind("username", username);

		q = baseDao.addInClauseBindUpdate(q, typesBindMap);
		q.execute();
	}
	
	// REMOVES--------------------------------------------------------------------------------------------------------------------------------------
	
	@SqlUpdate("DELETE FROM TWSS_FOLLOWERS WHERE SENDER_USERNAME=:username AND RECEIVER_USERNAME=:followingUserName")
	public abstract void removeFollowingUser(@Bind("username") String username, @Bind("followingUserName") String followingUserName);
	
	@SqlUpdate("DELETE FROM TWSS_NOTIFICATIONS WHERE SENDER_USERNAME=:senderUsername AND RECEIVER_USERNAME=:username AND TYPE=:notificationType")
	public abstract void deleteNotification(@Bind("senderUsername") String senderUsername, @Bind("username") String username, @Bind("notificationType") String notificationType);
	
	@SqlUpdate("DELETE FROM TWSS_REACTIONS WHERE USERNAME=:username AND UUID_POST=:uuidPost AND TYPE=:reactionType")
	public abstract void removeReaction(@Bind("username") String username, @Bind("uuidPost") String uuidPost, @Bind("reactionType") String reactionType);
	
	public void deleteAllReactions(IDBI dbi, List<String> uuidPost) {

		BaseDao baseDao = new BaseDao<>();
		String query = "DELETE FROM TWSS_REACTIONS WHERE UUID_POST IN (<uuidPost>)";

		Map<String, List<String>> typesBindMap = new HashMap<>();
		typesBindMap.put("uuidPost", uuidPost);
		query = baseDao.generateBingIdentifier(query, typesBindMap);

		Handle h = dbi.open();	
		Update q = h.createStatement(query);

		q = baseDao.addInClauseBindUpdate(q, typesBindMap);
		q.execute();
	}
	
	public void deleteAttachments(IDBI dbi, String username, List<String> uuidPosts) {

		BaseDao baseDao = new BaseDao<>();
		String query = "DELETE FROM TWSS_ATTACHMENTS WHERE USERNAME=:username AND UUID_POST IN (<uuidPosts>)";

		Map<String, List<String>> typesBindMap = new HashMap<>();
		typesBindMap.put("uuidPosts", uuidPosts);
		query = baseDao.generateBingIdentifier(query, typesBindMap);

		Handle h = dbi.open();	
		Update q = h.createStatement(query)
		.bind("username", username);

		q = baseDao.addInClauseBindUpdate(q, typesBindMap);
		q.execute();
	}
	
	public void deletePosts(IDBI dbi, String username, List<String> uuidPosts) {

		BaseDao baseDao = new BaseDao<>();
		String query = "DELETE FROM TWSS_POSTS WHERE USERNAME=:username AND UUID IN (<uuidPosts>)";

		Map<String, List<String>> typesBindMap = new HashMap<>();
		typesBindMap.put("uuidPosts", uuidPosts);
		query = baseDao.generateBingIdentifier(query, typesBindMap);

		Handle h = dbi.open();	
		Update q = h.createStatement(query)
		.bind("username", username);

		q = baseDao.addInClauseBindUpdate(q, typesBindMap);
		q.execute();
	}

	
}