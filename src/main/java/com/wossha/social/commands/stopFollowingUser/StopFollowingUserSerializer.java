package com.wossha.social.commands.stopFollowingUser;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.commands.stopFollowingUser.model.StopFollowingUser;

@Component
public class StopFollowingUserSerializer implements ICommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private StopFollowingUserCommand command;
	
	@Override
	public ICommand<StopFollowingUser> deserialize(String json) throws IOException {
		StopFollowingUser dto = m.readValue(json, StopFollowingUser.class);
        command.setData(dto);
        return command;
	}

}
