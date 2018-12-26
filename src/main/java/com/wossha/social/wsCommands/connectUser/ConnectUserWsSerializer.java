package com.wossha.social.wsCommands.connectUser;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.social.wsCommands.WSCommand;
import com.wossha.social.wsCommands.WSCommandSerializer;
import com.wossha.social.wsCommands.connectUser.model.ConnectUser;

@Component
public class ConnectUserWsSerializer extends WSCommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private ConnectUserWsCommand command;
	
	@Override
	public WSCommand<ConnectUser> deserialize(String json) throws IOException {
		ConnectUser dto = m.readValue(json, ConnectUser.class);
        command.setData(dto);
        return command;
	}

}
