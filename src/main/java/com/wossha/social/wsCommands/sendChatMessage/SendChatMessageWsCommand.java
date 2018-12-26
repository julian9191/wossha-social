package com.wossha.social.wsCommands.sendChatMessage;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.infrastructure.mapper.MapperDozer;
import com.wossha.social.infrastructure.repositories.SocialRepository;
import com.wossha.social.wsCommands.WSCommand;
import com.wossha.social.wsCommands.WsDestinations;
import com.wossha.social.wsCommands.sendChatMessage.model.SendChatMessage;

@Component
public class SendChatMessageWsCommand extends WSCommand<SendChatMessage> {

	private SendChatMessage data;
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
	public SendChatMessage data() {
		return this.data;
	}

	@Override
	public void setData(SendChatMessage data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();

		repo.saveChatMessage(data.getMessage());
		
		data.getMessage().setSendOn(new Date());
		messagingTemplate.convertAndSendToUser(data.getMessage().getToId(), WsDestinations.SEND_TO_USER_DEST.getValue(),
				data.getMessage());

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