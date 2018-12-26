package com.wossha.social.wsCommands;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.wsCommands.connectUser.ConnectUserWsSerializer;
import com.wossha.social.wsCommands.sendChatMessage.SendChatMessageWsSerializer;

@Service
public class WsCommandSerializers {

    private final Map<String, ICommandSerializer> processors = new HashMap<>();
    
    //serializers
    private SendChatMessageWsSerializer sendChatMessageWsSerializer;
    private ConnectUserWsSerializer connectUserWsSerializer;

	public void initMapper() {
        processors.put("SendChatMessage", sendChatMessageWsSerializer);
        processors.put("ConnectUser", connectUserWsSerializer);
    }

    public ICommandSerializer get(String commandName) {
        return processors.get(commandName);
    }

	public void setSendChatMessageWsSerializer(SendChatMessageWsSerializer sendChatMessageWsSerializer) {
		this.sendChatMessageWsSerializer = sendChatMessageWsSerializer;
	}

	public void setConnectUserWsSerializer(ConnectUserWsSerializer connectUserWsSerializer) {
		this.connectUserWsSerializer = connectUserWsSerializer;
	}
}
