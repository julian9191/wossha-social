package com.wossha.social.commands;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wossha.msbase.commands.ICommandSerializer;

@Service
public class CommandSerializers {

    private final Map<String, ICommandSerializer> processors = new HashMap<>();
    
    //serializers
    @Autowired
    //private SavePictureSerializer savePictureSerializer;

	public void initMapper() {
        //processors.put("SavePicture", savePictureSerializer);
    }

    public ICommandSerializer get(String commandName) {
        return processors.get(commandName);
    }
    
    /*public void setModifyUserSerializer(SavePictureSerializer savePictureSerializer) {
		this.savePictureSerializer = savePictureSerializer;
	}*/
}
