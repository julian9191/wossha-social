package com.wossha.social.infrastructure.jms;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import com.wossha.json.events.events.api.EventSerializer;
@Component
public class EventSerializers {

private final HashMap<String, EventSerializer> listeners = new HashMap<>();
    
    //@Autowired
    //private SavePictureEventSerializer savePictureEventSerializer;

    public void initMapper() {
        //listeners.put("SavePicture", savePictureEventSerializer);
    }


    public EventSerializer get(String eventName) {
        return listeners.get(eventName);
    }


	/*public void setSavePictureEventSerializer(SavePictureEventSerializer savePictureEventSerializer) {
		this.savePictureEventSerializer = savePictureEventSerializer;
	}*/

}