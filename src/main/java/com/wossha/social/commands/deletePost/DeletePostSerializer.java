package com.wossha.social.commands.deletePost;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.commands.deletePost.model.DeletePost;

@Component
public class DeletePostSerializer implements ICommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private DeletePostCommand command;
	
	@Override
	public ICommand<DeletePost> deserialize(String json) throws IOException {
		DeletePost dto = m.readValue(json, DeletePost.class);
        command.setData(dto);
        return command;
	}

}
