package com.wossha.social.commands.acceptFollow;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.commands.acceptFollow.model.AcceptFollow;
import com.wossha.social.dto.Notification;
import com.wossha.social.infrastructure.enums.NotificationsEnum;
import com.wossha.social.infrastructure.filters.UsernameInfoAuthenticationToken;
import com.wossha.social.infrastructure.repositories.SocialRepository;
import com.wossha.social.infrastructure.websocket.model.AcceptFollowMessage;
import com.wossha.social.wsCommands.WsDestinations;

@Component
public class AcceptFollowCommand implements ICommand<AcceptFollow> {

	private AcceptFollow data;
	private String username;

	@Autowired
	private SocialRepository repo;
	
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

	@Override
	public String commandName() {
		return "FollowUser";
	}

	@Override
	public AcceptFollow data() {
		return this.data;
	}

	@Override
	public void setData(AcceptFollow data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();

		repo.changeStateFollowUser(data.getSenderUsername(), data.getUsername(), 1);
		repo.deleteNotification(data.getSenderUsername(), data.getUsername(), data.getNotificationType());
		
		UsernameInfoAuthenticationToken auth = (UsernameInfoAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		
		Notification notificacion = createFollowRequestNotificationDto(username, auth.getFirstName()+" "+auth.getLastName(), auth.getProfilePicture(), data.getSenderUsername());
		
		repo.addNotification(notificacion);
		
		AcceptFollowMessage acceptFollowMessage = new AcceptFollowMessage(username, data.getSenderUsername(), notificacion);
		messagingTemplate.convertAndSendToUser(data.getSenderUsername(), WsDestinations.SEND_TO_USER_DEST.getValue(),
				acceptFollowMessage);
		
		result.setMessage("Ahora @"+data.getSenderUsername()+" te sigue");

		return result;
	}
	
	private Notification createFollowRequestNotificationDto(String senderUsername, String senderName, String senderPicture, String receiverUsername) {
		Notification notificacion = new Notification(NotificationsEnum.ACCEPT_FOLLOW.name(), receiverUsername, senderUsername, senderName, senderPicture, false, false, new Date());
		return notificacion;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
