package com.wossha.social.commands.createPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.commands.createPost.model.CreatePost;
import com.wossha.social.dto.post.Post;
import com.wossha.social.infrastructure.factories.PostFactory;
import com.wossha.social.infrastructure.repositories.SocialRepository;

@Component
public class CreatePostCommand implements ICommand<CreatePost> {

	private CreatePost data;
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
	public CreatePost data() {
		return this.data;
	}

	@Override
	public void setData(CreatePost data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();
		
		Post post = PostFactory.createPost(data);
		repo.addPost(post);
		
		return result;
	}
	

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
