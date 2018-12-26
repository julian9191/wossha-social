package com.wossha.social.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.wossha.social.wsCommands.WsCommandSerializers;
import com.wossha.social.wsCommands.connectUser.ConnectUserWsCommand;
import com.wossha.social.wsCommands.connectUser.ConnectUserWsSerializer;
import com.wossha.social.wsCommands.sendChatMessage.SendChatMessageWsCommand;
import com.wossha.social.wsCommands.sendChatMessage.SendChatMessageWsSerializer;

@Configuration
public class WSBeansConfig {
	
	//commands--------------------------------------------------

	@Bean
	public SendChatMessageWsCommand sendChatMessageWsCommand() {
		return new SendChatMessageWsCommand();
	}
	
	@Bean
	public ConnectUserWsCommand connectUserWsCommand() {
		return new ConnectUserWsCommand();
	}
	
	//serializers--------------------------------------------------
	
	@Bean
	public SendChatMessageWsSerializer sendChatMessageWsSerializer() {
		return new SendChatMessageWsSerializer();
	}
	
	@Bean
	public ConnectUserWsSerializer connectUserWsSerializer() {
		return new ConnectUserWsSerializer();
	}
	
	//--------------------------------------------------------------
	
	@Bean
	public WsCommandSerializers wsCommandSerializers() {
		WsCommandSerializers cs = new WsCommandSerializers();
		cs.setSendChatMessageWsSerializer(sendChatMessageWsSerializer());
		cs.setConnectUserWsSerializer(connectUserWsSerializer());
		cs.initMapper();
		return cs;
	}
	
}
