package com.wossha.social.commands.refuseFollow;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.commands.refuseFollow.model.RefuseFollow;

@Component
public class RefuseFollowSerializer implements ICommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private RefuseFollowCommand command;
	
	@Override
	public ICommand<RefuseFollow> deserialize(String json) throws IOException {
		RefuseFollow dto = m.readValue(json, RefuseFollow.class);
        command.setData(dto);
        return command;
	}

}
