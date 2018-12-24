package com.wossha.social.wsCommands;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.wsCommands.sendChatMessage.SendChatMessageWsSerializer;

@Service
public class WsCommandSerializers {

    private final Map<String, ICommandSerializer> processors = new HashMap<>();
    
    //serializers
    private SendChatMessageWsSerializer sendChatMessageWsSerializer;

	public void initMapper() {
        processors.put("SendChatMessage", sendChatMessageWsSerializer);
    }

    public ICommandSerializer get(String commandName) {
        return processors.get(commandName);
    }

	public void setSendChatMessageWsSerializer(SendChatMessageWsSerializer sendChatMessageWsSerializer) {
		this.sendChatMessageWsSerializer = sendChatMessageWsSerializer;
	}

}
