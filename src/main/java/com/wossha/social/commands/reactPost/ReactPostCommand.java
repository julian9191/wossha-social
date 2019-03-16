package com.wossha.social.commands.reactPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.wossha.json.events.services.UUIDGenerator;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.commands.reactPost.model.ReactPost;
import com.wossha.social.dto.post.Post;
import com.wossha.social.dto.post.Reaction;
import com.wossha.social.infrastructure.factories.PostFactory;
import com.wossha.social.infrastructure.repositories.SocialRepository;

@Component
public class ReactPostCommand implements ICommand<ReactPost> {

	private ReactPost data;
	private String username;

	@Autowired
	private SocialRepository repo;
	
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

	@Override
	public String commandName() {
		return "FollowUser";
	}

	@Override
	public ReactPost data() {
		return this.data;
	}

	@Override
	public void setData(ReactPost data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();
		
		Reaction prevReaction = repo.getReaction(username, data.getUuidPost());
		
		if(prevReaction != null && prevReaction.getType().equals(data.getReactionType())) {
			repo.removeReaction(username, data.getUuidPost(), data.getReactionType());
			result.setMessage("remove");
		}else {
			
			Reaction reaction = new Reaction();
			reaction.setUuid(UUIDGenerator.generateUUID());
			reaction.setType(data.getReactionType());
			reaction.setUuidPost(data.getUuidPost());
			reaction.setUsername(username);
			
			if(prevReaction == null) {
				repo.addReaction(reaction);
				result.setMessage("ok");
			}else {
				repo.updateReactionType(username, data.getUuidPost(), data.getReactionType());
				result.setMessage(prevReaction.getType());
			}
		}

		return result;
	}
	

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
