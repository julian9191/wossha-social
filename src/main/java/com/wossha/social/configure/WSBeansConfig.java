package com.wossha.social.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.wossha.social.infrastructure.repositories.SocialRepository;
import com.wossha.social.wsCommands.WsCommandSerializers;
import com.wossha.social.wsCommands.sendChatMessage.SendChatMessageWsCommand;
import com.wossha.social.wsCommands.sendChatMessage.SendChatMessageWsSerializer;

@Configuration
public class WSBeansConfig {
	
	//commands--------------------------------------------------

	@Bean
	public SendChatMessageWsCommand sendChatMessageWsCommand() {
		return new SendChatMessageWsCommand();
	}
	
	//serializers--------------------------------------------------
	
	@Bean
	public SendChatMessageWsSerializer sendChatMessageWsSerializer() {
		return new SendChatMessageWsSerializer();
	}
	
	//--------------------------------------------------------------
	
	@Bean
	public WsCommandSerializers wsCommandSerializers() {
		WsCommandSerializers cs = new WsCommandSerializers();
		cs.setSendChatMessageWsSerializer(sendChatMessageWsSerializer());
		cs.initMapper();
		return cs;
	}
	
}
