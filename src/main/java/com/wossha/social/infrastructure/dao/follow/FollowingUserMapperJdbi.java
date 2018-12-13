package com.wossha.social.infrastructure.dao.follow;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import com.wossha.social.dto.FollowingUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowingUserMapperJdbi implements ResultSetMapper<FollowingUser> {
	
    @Override
    public FollowingUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        
		return new FollowingUser(
				r.getString("USERNAME"),
                r.getInt("STATE")
        );
        
    }
}