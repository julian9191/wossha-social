package com.wossha.social.infrastructure.websocket.listeners;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.social.userConnectedEvent.Message;
import com.wossha.json.events.events.social.userConnectedEvent.UserConnectionEvent;
import com.wossha.social.WosshaSocialApplication;
import com.wossha.social.dto.FollowingUser;
import com.wossha.social.infrastructure.repositories.SocialRepository;
import com.wossha.social.infrastructure.websocket.model.ChatMessage;
import com.wossha.social.infrastructure.websocket.model.ConnectedUserMessage;
import com.wossha.social.infrastructure.websocket.model.DisconnectedUserMessage;
import com.wossha.social.wsCommands.WsDestinations;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @Autowired
    JmsTemplate jmsTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private SocialRepository repo;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    	System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            System.out.println("User Disconnected : " + username);

            /*ChatMessage chatMessage = new ChatMessage();
            chatMessage.setFromId(username);*/
            
            Message message = new Message(username, 0);
            Event userConnectedEvent = new UserConnectionEvent(WosshaSocialApplication.APP_NAME, username, message);
            
            List<Event> events = new ArrayList<>();
            events.add(userConnectedEvent);
            
            try {
            	
    			publishEvents(events);
    		} catch (JsonProcessingException e) {
    			e.printStackTrace();
    		}
            
            List<FollowingUser> followingUsers = repo.getFollowingUsers(username);
            
            DisconnectedUserMessage disconnectedUserMessage = new DisconnectedUserMessage(username);
            for (FollowingUser followingUser : followingUsers) {
            	messagingTemplate.convertAndSendToUser(followingUser.getUsername(), WsDestinations.SEND_TO_USER_DEST.getValue(),
            			disconnectedUserMessage);
    		}
        }
    }
    
    private void publishEvents(List<Event> events) throws JsonProcessingException {
		for (Event event : events) {
			ObjectMapper mapper = new ObjectMapper();
			String jsonEvent = mapper.writeValueAsString(event);
			String queue = env.getProperty("EVENT."+event.getName()+".QUEUES");
			jmsTemplate.convertAndSend(queue, jsonEvent);
		}
	}
}