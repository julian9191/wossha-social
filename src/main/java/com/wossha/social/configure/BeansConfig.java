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
import com.wossha.social.commands.changeNotifToViewed.ChangeNotifToViewedCommand;
import com.wossha.social.commands.changeNotifToViewed.ChangeNotifToViewedSerializer;
import com.wossha.social.commands.createPost.CreatePostCommand;
import com.wossha.social.commands.createPost.CreatePostSerializer;
import com.wossha.social.commands.followUser.FollowUserCommand;
import com.wossha.social.commands.followUser.FollowUserSerializer;
import com.wossha.social.commands.reactPost.ReactPostCommand;
import com.wossha.social.commands.reactPost.ReactPostSerializer;
import com.wossha.social.commands.refuseFollow.RefuseFollowCommand;
import com.wossha.social.commands.refuseFollow.RefuseFollowSerializer;
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
	
	@Bean
	public ChangeNotifToViewedCommand changeNotifToViewedCommand() {
		return new ChangeNotifToViewedCommand();
	}
	
	@Bean
	public RefuseFollowCommand refuseFollowCommand() {
		return new RefuseFollowCommand();
	}
	
	@Bean
	public CreatePostCommand createPostCommand() {
		return new CreatePostCommand();
	}
	
	@Bean
	public ReactPostCommand reactPostCommand() {
		return new ReactPostCommand();
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
	
	@Bean
	public ChangeNotifToViewedSerializer changeNotifToViewedSerializer() {
		return new ChangeNotifToViewedSerializer();
	}
	
	@Bean
	public RefuseFollowSerializer refuseFollowSerializer() {
		return new RefuseFollowSerializer();
	}
	
	@Bean
	public CreatePostSerializer createPostSerializer() {
		return new CreatePostSerializer();
	}
	
	@Bean
	public ReactPostSerializer reactPostSerializer() {
		return new ReactPostSerializer();
	}
	
	
	//--------------------------------------------------------------
	
	@Bean
	public CommandSerializers commandSerializers() {
		CommandSerializers cs = new CommandSerializers();
		cs.setFollowUserSerializer(followUserSerializer());
		cs.setStopFollowingUserSerializer(stopFollowingUserSerializer());
		cs.setAcceptFollowSerializer(acceptFollowSerializer());
		cs.setChangeNotifToViewedSerializer(changeNotifToViewedSerializer());
		cs.setRefuseFollowSerializer(refuseFollowSerializer());
		cs.setCreatePostSerializer(createPostSerializer());
		cs.setReactPostSerializer(reactPostSerializer());
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
