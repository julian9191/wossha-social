package com.wossha.social.infrastructure.factories;

import com.wossha.json.events.services.UUIDGenerator;
import com.wossha.social.commands.createPost.model.CreatePost;
import com.wossha.social.dto.post.Post;
import com.wossha.social.infrastructure.enums.PostTypesEnum;

public class PostFactory {

	public static Post createPost(CreatePost createPost) {
		Post post = new Post(UUIDGenerator.generateUUID());
		post.setUsername(createPost.getUsername());
		
		if(createPost.getImages() == null || createPost.getImages().isEmpty()) {
			post.setType(PostTypesEnum.SIMPLE_POST.name());
		}else {
			post.setType(PostTypesEnum.IMAGE_POST.name());
		}
		
		
		if (createPost.getUuidParent() != null && !createPost.getUuidParent().equals("")) {
			post.setUuidParent(createPost.getUuidParent());
		}
		
		if (createPost.getText() != null && !createPost.getText().equals("")) {
			post.setText(createPost.getText());
		}

		return post;
	}

}
