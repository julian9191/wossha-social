package com.wossha.social.commands.reactPost;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.commands.reactPost.model.ReactPost;

@Component
public class ReactPostSerializer implements ICommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private ReactPostCommand command;
	
	@Override
	public ICommand<ReactPost> deserialize(String json) throws IOException {
		ReactPost dto = m.readValue(json, ReactPost.class);
        command.setData(dto);
        return command;
	}

}
