package com.wossha.social.wsCommands.connectUser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.social.userConnectedEvent.Message;
import com.wossha.json.events.events.social.userConnectedEvent.UserConnectionEvent;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.WosshaSocialApplication;
import com.wossha.social.infrastructure.mapper.MapperDozer;
import com.wossha.social.infrastructure.repositories.SocialRepository;
import com.wossha.social.wsCommands.WSCommand;
import com.wossha.social.wsCommands.WsDestinations;
import com.wossha.social.wsCommands.connectUser.model.ConnectUser;

@Component
public class ConnectUserWsCommand extends WSCommand<ConnectUser> {

	private ConnectUser data;
	private SimpMessageSendingOperations messagingTemplate;
	private SimpMessageHeaderAccessor headerAccessor;
	private String username;
	private MapperDozer map = new MapperDozer();

	@Autowired
	private SocialRepository repo;

	@Override
	public String commandName() {
		return "FollowUser";
	}

	@Override
	public ConnectUser data() {
		return this.data;
	}

	@Override
	public void setData(ConnectUser data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();

		// Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", data.getMessage().getSender());
        
        Message message = new Message(data.getMessage().getSender(), 1);
        Event userConnectedEvent = new UserConnectionEvent(WosshaSocialApplication.APP_NAME, data.getMessage().getSender(), message);
        result.addEvent(userConnectedEvent);
        
        messagingTemplate.convertAndSend(WsDestinations.PUBLIC_TOPIC.getValue(), data.getMessage());

		return result;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setMessagingTemplate(SimpMessageSendingOperations messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public void setHeaderAccessor(SimpMessageHeaderAccessor headerAccessor) {
		this.headerAccessor = headerAccessor;
		
	}

}
