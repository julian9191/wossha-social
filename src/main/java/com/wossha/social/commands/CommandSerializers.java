package com.wossha.social.commands;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.wossha.msbase.commands.ICommandSerializer;
import com.wossha.social.commands.followUser.FollowUserSerializer;
import com.wossha.social.commands.stopFollowingUser.StopFollowingUserSerializer;

@Service
public class CommandSerializers {

    private final Map<String, ICommandSerializer> processors = new HashMap<>();
    
    //serializers
    private FollowUserSerializer followUserSerializer;
    private StopFollowingUserSerializer stopFollowingUserSerializer;

	public void initMapper() {
        processors.put("FollowUser", followUserSerializer);
        processors.put("StopFollowingUser", stopFollowingUserSerializer);
    }

    public ICommandSerializer get(String commandName) {
        return processors.get(commandName);
    }

	public void setFollowUserSerializer(FollowUserSerializer followUserSerializer) {
		this.followUserSerializer = followUserSerializer;
	}

	public void setStopFollowingUserSerializer(StopFollowingUserSerializer stopFollowingUserSerializer) {
		this.stopFollowingUserSerializer = stopFollowingUserSerializer;
	}
    
}
