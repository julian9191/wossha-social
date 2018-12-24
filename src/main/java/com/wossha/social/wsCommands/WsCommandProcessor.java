package com.wossha.social.wsCommands;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.json.events.events.api.Event;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.msbase.controllers.ControllerWrapper;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class WsCommandProcessor extends ControllerWrapper{

    private Logger logger = LoggerFactory.getLogger(WsCommandProcessor.class);
    private final static ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    WsCommandSerializers wsCommandSerializers;
    
    @Autowired
	private Environment env;
    
    @Autowired
    JmsTemplate jmsTemplate;

    @MessageMapping("/social.command")
    public void processCommand(@Payload String json, Principal principal) {
        try {
        	
        	JsonNode root = mapper.readTree(json);
            JsonNode jCommand = root.path("commandName");

            ICommandSerializer cs = wsCommandSerializers.get(jCommand.asText());
            
            @SuppressWarnings("rawtypes")
			ICommand command = cs.deserialize(json);

    		String username = principal.toString();
            command.setUsername(username);
            CommandResult result = command.execute();
            
            logger.debug("WS command generated: "+json);
            
            publishEvents(result.getEvents());

        } catch (IOException e) {
        	logger.debug("IO Exception in WsCommandProcessor: "+e.getMessage());
        	e.printStackTrace();
		} catch (BusinessException e) {
			logger.debug("BusinessException Exception in WsCommandProcessor: "+e.getMessage());
		} catch (TechnicalException e) {
			logger.debug("TechnicalException Exception in WsCommandProcessor: "+e.getMessage());
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
