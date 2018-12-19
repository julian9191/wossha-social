package com.wossha.social.infrastructure.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.api.EventProcessor;
import com.wossha.json.events.events.api.EventSerializer;
import com.wossha.json.events.exceptions.RecoverableException;
import com.wossha.social.WosshaSocialApplication;;

@Component
public class EventListener {
	
	private final static Logger logger = LoggerFactory.getLogger(EventListener.class);

	@Autowired
	EventSerializers eventSerializers;
	
	@JmsListener(destination = "social.queue")
    public void consume(String json) {
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            
            logger.debug("New Event received in "+WosshaSocialApplication.APP_NAME+": "+ root.path("name").asText());
            
            EventSerializer es = eventSerializers.get(root.path("name").asText());
            if (es != null) {
                EventProcessor eventProcessor = es.deserialize(json);
                List<Event> events = eventProcessor.execute();
                //publishEvents(events, "jms/PROTEC.INCAPACIDAD.SOLICITUD");
            } else {
                logger.debug("Event received in "+WosshaSocialApplication.APP_NAME+" without listener " + json);
            }
        }catch (RecoverableException e) {
            logger.error("RecoverableException: " + json, e );
            //publishErrorEvent(json,"jms/Q.SUBS.APP.SOLICITUD_INCAPACIDAD");
        }  catch (Exception e) {
        	logger.error("Uncontrolled error", e);
        }
        
    }

	
}
