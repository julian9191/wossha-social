package com.wossha.social.infrastructure.dao.follow;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import com.wossha.social.dto.post.Attachment;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttachmentMapperJdbi implements ResultSetMapper<Attachment> {
	
    @Override
    public Attachment map(int index, ResultSet r, StatementContext ctx) throws SQLException {
           	    	
    	return new Attachment(
    			r.getInt("ID"),
    			r.getString("UUID"),
    			r.getString("TYPE"),
    			r.getString("UUID_POST"),
    			r.getString("URL"),
    			r.getString("USERNAME"),
    			r.getTimestamp("CREATED"),
				r.getTimestamp("MODIFIED")
    	);
        
    }
}