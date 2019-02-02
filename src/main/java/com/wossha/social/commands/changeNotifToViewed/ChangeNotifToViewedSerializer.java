package com.wossha.social.commands.changeNotifToViewed;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.commands.changeNotifToViewed.model.ChangeNotifToViewed;

@Component
public class ChangeNotifToViewedSerializer implements ICommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private ChangeNotifToViewedCommand command;
	
	@Override
	public ICommand<ChangeNotifToViewed> deserialize(String json) throws IOException {
		ChangeNotifToViewed dto = m.readValue(json, ChangeNotifToViewed.class);
        command.setData(dto);
        return command;
	}

}
