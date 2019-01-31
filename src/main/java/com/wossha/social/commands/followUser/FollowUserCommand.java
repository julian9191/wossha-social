package com.wossha.social.commands.followUser;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.wossha.json.events.services.UUIDGenerator;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.commands.followUser.model.FollowUser;
import com.wossha.social.dto.Notification;
import com.wossha.social.infrastructure.enums.NotificationsEnum;
import com.wossha.social.infrastructure.mapper.MapperDozer;
import com.wossha.social.infrastructure.repositories.SocialRepository;
import com.wossha.social.infrastructure.websocket.model.AcceptFollowMessage;
import com.wossha.social.infrastructure.websocket.model.FollowRequestNotifMessage;
import com.wossha.social.wsCommands.WsDestinations;

@Component
public class FollowUserCommand implements ICommand<FollowUser> {

	private FollowUser data;
	private String username;
	
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	private SocialRepository repo;

	@Override
	public String commandName() {
		return "FollowUser";
	}

	@Override
	public FollowUser data() {
		return this.data;
	}

	@Override
	public void setData(FollowUser data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();
		
		FollowUser followUser = repo.getFollowersRelationship(data.getSenderUsername(), data.getReceiverUsername());
		
		if(followUser != null) {
			if(followUser.getState()==1) {
				throw new BusinessException("Ya estás siguiendo a este usuario");
			}else {
				throw new BusinessException("Ya enviaste una solicitud para seguir a este usuario");
			}
			
		}
		
		data.setUuid(UUIDGenerator.generateUUID());
		data.setState(0);
		repo.add(data);
		result.setMessage("La solicitud para seguir al usuario "+data.getReceiverUsername()+" se ha enviado");
		
		Notification notificacion = createFollowRequestNotificationDto(username, data.getSenderName(), data.getSenderPicture(), data.getReceiverUsername());
		repo.addNotification(notificacion);

		FollowRequestNotifMessage followRequestNotifMessage = new FollowRequestNotifMessage(data.getSenderUsername(), username, notificacion);
		messagingTemplate.convertAndSendToUser(data.getReceiverUsername(), WsDestinations.SEND_TO_USER_DEST.getValue(),
				followRequestNotifMessage);

		return result;
	}
	
	
	


	private Notification createFollowRequestNotificationDto(String senderUsername, String senderName, String senderPicture, String receiverUsername) {
		Notification notificacion = new Notification(NotificationsEnum.FOLLOW_REQUEST.name(), receiverUsername, senderUsername, senderName, senderPicture, false, false, new Date());
		return notificacion;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
