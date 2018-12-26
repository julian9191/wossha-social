package com.wossha.social.wsCommands.sendChatMessage;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.wsCommands.WSCommand;
import com.wossha.social.wsCommands.WSCommandSerializer;
import com.wossha.social.wsCommands.sendChatMessage.model.SendChatMessage;

@Component
public class SendChatMessageWsSerializer extends WSCommandSerializer {
	
	@Autowired
	private ObjectMapper m;
	
	@Autowired
	private SendChatMessageWsCommand command;
	
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
	
	@Override
	public WSCommand<SendChatMessage> deserialize(String json) throws IOException {
		SendChatMessage dto = m.readValue(json, SendChatMessage.class);
        command.setData(dto);
        command.setMessagingTemplate(messagingTemplate);
        return command;
	}

}
