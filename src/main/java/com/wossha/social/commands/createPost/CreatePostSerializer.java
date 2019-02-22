package com.wossha.social.commands.createPost;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.commands.createPost.model.CreatePost;

@Component
public class CreatePostSerializer implements ICommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private CreatePostCommand command;
	
	@Override
	public ICommand<CreatePost> deserialize(String json) throws IOException {
		CreatePost dto = m.readValue(json, CreatePost.class);
        command.setData(dto);
        return command;
	}

}
