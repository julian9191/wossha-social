package com.wossha.social.infrastructure.dao.follow;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import com.wossha.social.dto.Notification;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationMapperJdbi implements ResultSetMapper<Notification> {
	
    @Override
    public Notification map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        
		return new Notification(
				r.getInt("ID"),
				r.getString("TYPE"),
				r.getString("RECEIVER_USERNAME"),
				r.getString("SENDER_USERNAME"),
				r.getString("SENDER_NAME"),
				r.getString("MESSAGE"),
				r.getBoolean("VIEWED"),
				r.getBoolean("OPENED"),
				r.getTimestamp("CREATED")
        );
        
    }
}