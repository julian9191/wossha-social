package com.wossha.social.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.social.userConnectedEvent.Message;
import com.wossha.json.events.events.pictures.SavePictureEvent.SavePictureEvent;
import com.wossha.json.events.events.social.userConnectedEvent.UserConnectionEvent;
import com.wossha.msbase.enums.PictureTypesEnum;
import com.wossha.social.WosshaSocialApplication;
import com.wossha.social.dto.FollowingUser;
import com.wossha.social.dto.UserChatStatus;
import com.wossha.social.infrastructure.repositories.SocialRepository;
import com.wossha.social.infrastructure.websocket.model.ChatMessage;
import com.wossha.social.infrastructure.websocket.model.ChatMessage2;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class ChatController {

	@Autowired
	private SocialRepository repo;
	
	@Autowired
    JmsTemplate jmsTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
	
	SimpMessageHeaderAccessor headerAccessor;
	
    /*@MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }*/
    
    @MessageMapping("/chat.sendMessage")
	//@SendToUser("/queue/reply")
	public void sendMessage(@Payload ChatMessage chatMessage) throws Exception {
    	//messagingTemplate.convertAndSendToUser(chatMessage.getToId(), "/queue/reply", chatMessage);
    	//messagingTemplate.convertAndSendToUser(chatMessage.getFromId(), "/queue/reply", chatMessage);
    	//return chatMessage;
    	messagingTemplate.convertAndSend("/queue/reply-" + chatMessage.getToId(), chatMessage);
	}
    
	/*@MessageMapping("/chat.sendMessage")
    @SendToUser("/queue/reply")
    public ChatMessage sendMessage(
    		@Payload ChatMessage chatMessage, 
    		Principal principal) throws Exception {
    	return chatMessage;
    }*/

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage2 addUser(@Payload ChatMessage2 chatMessage, 
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        
        Message message = new Message(chatMessage.getSender(), 1);
        Event userConnectedEvent = new UserConnectionEvent(WosshaSocialApplication.APP_NAME, chatMessage.getSender(), message);
        
        List<Event> events = new ArrayList<>();
        events.add(userConnectedEvent);
        
        try {
        	
			publishEvents(events);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        return chatMessage;
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