package com.wossha.social.commands.followUser;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.commands.followUser.model.FollowUser;

@Component
public class FollowUserSerializer implements ICommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private FollowUserCommand command;
	
	@Override
	public ICommand<FollowUser> deserialize(String json) throws IOException {
		FollowUser dto = m.readValue(json, FollowUser.class);
        command.setData(dto);
        return command;
	}

}
