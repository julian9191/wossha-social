package com.wossha.social.infrastructure.factories;

import com.wossha.json.events.services.UUIDGenerator;
import com.wossha.social.commands.createPost.model.CreatePost;
import com.wossha.social.dto.post.Post;
import com.wossha.social.infrastructure.enums.PostTypesEnum;

public class PostFactory {

	public static Post createPost(CreatePost createPost) {
		Post post = new Post(UUIDGenerator.generateUUID());
		post.setType(PostTypesEnum.SIMPLE_POST.name());
		post.setUsername(createPost.getUsername());
		
		if (createPost.getText() != null && !createPost.getText().equals("")) {
			post.setText(createPost.getText());
		}

		return post;
	}

}
