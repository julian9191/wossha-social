package com.wossha.social.infrastructure.dao.follow;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import com.wossha.social.infrastructure.websocket.model.ChatMessage;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatMessageMapperJdbi implements ResultSetMapper<ChatMessage> {
	
    @Override
    public ChatMessage map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        
		return new ChatMessage(
				r.getString("SENDER_USERNAME"),
				r.getString("RECEIVER_USERNAME"),
				r.getString("MESSAGE")
        );
        
    }
}