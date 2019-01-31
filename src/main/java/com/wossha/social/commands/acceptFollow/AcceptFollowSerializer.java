package com.wossha.social.commands.acceptFollow;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.commands.acceptFollow.model.AcceptFollow;

@Component
public class AcceptFollowSerializer implements ICommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private AcceptFollowCommand command;
	
	@Override
	public ICommand<AcceptFollow> deserialize(String json) throws IOException {
		AcceptFollow dto = m.readValue(json, AcceptFollow.class);
        command.setData(dto);
        return command;
	}

}
