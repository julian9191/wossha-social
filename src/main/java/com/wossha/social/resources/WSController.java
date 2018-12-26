package com.wossha.social.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.social.userConnectedEvent.Message;
import com.wossha.json.events.events.social.userConnectedEvent.UserConnectionEvent;
import com.wossha.social.WosshaSocialApplication;
import com.wossha.social.infrastructure.websocket.model.ChatMessage2;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class WSController {
	
	@Autowired
    JmsTemplate jmsTemplate;
	
	@Autowired
	private Environment env;
	
	SimpMessageHeaderAccessor headerAccessor;
    

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