package com.wossha.social.infrastructure.dao.follow;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import com.wossha.social.commands.followUser.model.FollowUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowUserMapperJdbi implements ResultSetMapper<FollowUser> {
	
    @Override
    public FollowUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        
		return new FollowUser(
				r.getString("SENDER_USERNAME"),
				r.getString("UUID"),
				r.getString("SENDER_USERNAME"),
                r.getString("RECEIVER_USERNAME"),
                r.getInt("STATE")
        );
        
    }
}