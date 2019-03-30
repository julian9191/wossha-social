package com.wossha.social.commands.createPost;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.pictures.SavePictureEvent.Message;
import com.wossha.json.events.events.pictures.SavePictureEvent.PictureInfo;
import com.wossha.json.events.events.pictures.SavePictureEvent.SavePictureEvent;
import com.wossha.json.events.services.UUIDGenerator;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.enums.PictureTypesEnum;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.msbase.models.PictureFileDTO;
import com.wossha.social.WosshaSocialApplication;
import com.wossha.social.commands.createPost.model.CreatePost;
import com.wossha.social.dto.post.Attachment;
import com.wossha.social.dto.post.Post;
import com.wossha.social.infrastructure.enums.PostTypesEnum;
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

		if (post.getType().equals(PostTypesEnum.IMAGE_POST.name())) {

			List<Attachment> attachments = getAttachments(data.getImages(), post);
			repo.addAttachments(attachments);

			Event savePictureEvent = generateSavePictureEvent(attachments);
			result.addEvent(savePictureEvent);
		}

		result.setMessage(post.getUuid());
		return result;
	}

	private List<Attachment> getAttachments(List<PictureFileDTO> images, Post post) {
		List<Attachment> attachments = new ArrayList<>();

		for (PictureFileDTO image : images) {
			Attachment attachment = new Attachment(null, UUIDGenerator.generateUUID(), PostTypesEnum.IMAGE_POST.name(),
					post.getUuid(), UUIDGenerator.generateUUID(), null, null);

			attachments.add(attachment);
		}

		return attachments;
	}

	private SavePictureEvent generateSavePictureEvent(List<Attachment> attachments) {
		List<PictureInfo> pictures = new ArrayList<>();

		for (int i = 0; i < attachments.size(); i++) {
			PictureInfo pictureInfo = new PictureInfo(attachments.get(i).getUrl(),
					data.getImages().get(i).getFilename(), data.getImages().get(i).getFiletype(),
					PictureTypesEnum.POST_PICTURE.name(), data.getImages().get(i).getSize(),
					data.getImages().get(i).getValue(), null);

			pictures.add(pictureInfo);
		}

		Message message = new Message(pictures);

		SavePictureEvent event = new SavePictureEvent(WosshaSocialApplication.APP_NAME, this.username, message);
		return event;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
