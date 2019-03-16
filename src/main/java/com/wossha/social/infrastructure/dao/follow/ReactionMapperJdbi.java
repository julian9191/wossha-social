package com.wossha.social.infrastructure.dao.follow;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import com.wossha.social.dto.post.Reaction;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReactionMapperJdbi implements ResultSetMapper<Reaction> {
	
    @Override
    public Reaction map(int index, ResultSet r, StatementContext ctx) throws SQLException {
           	
		return new Reaction(
				r.getInt("ID"),
				r.getString("UUID"),
				r.getString("TYPE"),
				r.getString("UUID_POST"),
				r.getString("USERNAME"),
				r.getTimestamp("CREATED")
        );
        
    }
}