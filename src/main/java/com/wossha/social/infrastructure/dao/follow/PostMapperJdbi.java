package com.wossha.social.infrastructure.dao.follow;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.wossha.social.dto.post.Post;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapperJdbi implements ResultSetMapper<Post> {
	
    @Override
    public Post map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        
    	//Integer id, String uuid, String type, String username, String text, String uuidParent,Timestamp created, Timestamp modified
    	
		return new Post(
				r.getInt("ID"),
				r.getString("UUID"),
				r.getString("TYPE"),
				r.getString("USERNAME"),
				r.getString("TEXT"),
				r.getString("UUID_PARENT"),
				r.getTimestamp("CREATED"),
				r.getTimestamp("MODIFIED")
        );
        
    }
}