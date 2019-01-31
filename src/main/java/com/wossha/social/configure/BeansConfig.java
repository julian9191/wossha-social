package com.wossha.social.configure;

import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.social.commands.CommandSerializers;
import com.wossha.social.commands.acceptFollow.AcceptFollowCommand;
import com.wossha.social.commands.acceptFollow.AcceptFollowSerializer;
import com.wossha.social.commands.followUser.FollowUserCommand;
import com.wossha.social.commands.followUser.FollowUserSerializer;
import com.wossha.social.commands.stopFollowingUser.StopFollowingUserCommand;
import com.wossha.social.commands.stopFollowingUser.StopFollowingUserSerializer;
import com.wossha.social.infrastructure.repositories.SocialRepository;

@Configuration
public class BeansConfig {
	
	@Bean
	public SocialRepository socialRepository() {
			return new SocialRepository();
	}

	
	//commands--------------------------------------------------

	@Bean
	public FollowUserCommand followUserCommand() {
		return new FollowUserCommand();
	}
	
	@Bean
	public StopFollowingUserCommand stopFollowingUserCommand() {
		return new StopFollowingUserCommand();
	}
	
	@Bean
	public AcceptFollowCommand acceptFollowCommand() {
		return new AcceptFollowCommand();
	}
	
	//serializers--------------------------------------------------
	
	@Bean
	public FollowUserSerializer followUserSerializer() {
		return new FollowUserSerializer();
	}
	
	@Bean
	public StopFollowingUserSerializer stopFollowingUserSerializer() {
		return new StopFollowingUserSerializer();
	}
	
	@Bean
	public AcceptFollowSerializer acceptFollowSerializer() {
		return new AcceptFollowSerializer();
	}
	
	
	//--------------------------------------------------------------
	
	@Bean
	public CommandSerializers commandSerializers() {
		CommandSerializers cs = new CommandSerializers();
		cs.setFollowUserSerializer(followUserSerializer());
		cs.setStopFollowingUserSerializer(stopFollowingUserSerializer());
		cs.setAcceptFollowSerializer(acceptFollowSerializer());
		cs.initMapper();
		return cs;
	}
	
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		TimeZone tz = TimeZone.getDefault();
		objectMapper.setTimeZone(tz);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}
	
	
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer init() {
	    return new Jackson2ObjectMapperBuilderCustomizer() {

	    	@Override
	        public void customize(Jackson2ObjectMapperBuilder builder) {
	            builder.timeZone(TimeZone.getDefault());
	        }
	    };
	}
	
}
