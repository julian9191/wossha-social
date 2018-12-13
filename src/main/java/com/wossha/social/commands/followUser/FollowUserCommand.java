package com.wossha.social.commands.followUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wossha.json.events.services.UUIDGenerator;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.commands.followUser.model.FollowUser;
import com.wossha.social.infrastructure.mapper.MapperDozer;
import com.wossha.social.infrastructure.repositories.SocialRepository;

@Component
public class FollowUserCommand implements ICommand<FollowUser> {

	private FollowUser data;
	private String username;
	private MapperDozer map = new MapperDozer();

	@Autowired
	private SocialRepository repo;

	@Override
	public String commandName() {
		return "FollowUser";
	}

	@Override
	public FollowUser data() {
		return this.data;
	}

	@Override
	public void setData(FollowUser data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();

		FollowUser followUser = repo.getFollowersRelationship(data.getSenderUsername(), data.getReceiverUsername());
		
		if(followUser != null) {
			if(followUser.getState()==1) {
				throw new BusinessException("Ya estás siguiendo a este usuario");
			}else {
				throw new BusinessException("Ya enviaste una solicitud para seguir a este usuario");
			}
			
		}
		
		data.setUuid(UUIDGenerator.generateUUID());
		data.setState(1);
		repo.add(data);
		result.setMessage("Ahora sigues al usuario "+data.getReceiverUsername());

		return result;
	}


	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
