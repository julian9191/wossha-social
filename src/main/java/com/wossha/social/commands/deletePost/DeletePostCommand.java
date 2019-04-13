package com.wossha.social.commands.deletePost;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import com.wossha.json.events.events.pictures.RemovePictureEvent.RemovePictureEvent;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.pictures.RemovePictureEvent.Message;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.WosshaSocialApplication;
import com.wossha.social.commands.deletePost.model.DeletePost;
import com.wossha.social.dto.post.Attachment;
import com.wossha.social.infrastructure.repositories.SocialRepository;

@Component
public class DeletePostCommand implements ICommand<DeletePost> {

	private DeletePost data;
	private String username;

	@Autowired
	private SocialRepository repo;

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@Override
	public String commandName() {
		return "DeletePost";
	}

	@Override
	public DeletePost data() {
		return this.data;
	}

	@Override
	public void setData(DeletePost data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();
		List<String> uuidPosts = new ArrayList<>();
		uuidPosts.add(data.getUuidPost());
		uuidPosts.addAll(data.getComments());
		
		uuidPosts = repo.getOwnPosts(uuidPosts, username);
		
		if(!uuidPosts.isEmpty()) {
			repo.deleteAttachments(uuidPosts, username);
			repo.deleteAllReactions(uuidPosts);
			repo.deletePosts(uuidPosts, username);
			
			if(!data.getAttachments().isEmpty()) {
				Event savePictureEvent = generateRemovePictureEvent(data.getAttachments().stream().map(Attachment::getUrl).collect(Collectors.toList()));
				result.addEvent(savePictureEvent);
			}
			
			result.setMessage("La publicación se ha eliminado correctamente");
		}else {
			result.setMessage("Ha ocurrido un error al intentar eliminar esta publicación");
		}

		
		return result;
	}


	private RemovePictureEvent generateRemovePictureEvent(List<String> uuidAttachment) {
		Message message = new Message(uuidAttachment);
		RemovePictureEvent event = new RemovePictureEvent(WosshaSocialApplication.APP_NAME, this.username, message);
		return event;
	}
	

	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	
}
