package com.wossha.social.commands.stopFollowingUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.commands.followUser.model.FollowUser;
import com.wossha.social.commands.stopFollowingUser.model.StopFollowingUser;
import com.wossha.social.infrastructure.repositories.SocialRepository;

@Component
public class StopFollowingUserCommand implements ICommand<StopFollowingUser> {

	private StopFollowingUser data;
	private String username;

	@Autowired
	private SocialRepository repo;

	@Override
	public String commandName() {
		return "FollowUser";
	}

	@Override
	public StopFollowingUser data() {
		return this.data;
	}

	@Override
	public void setData(StopFollowingUser data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();

		FollowUser followUser = repo.getFollowersRelationship(data.getUsername(), data.getFollowingUserName());
		
		if(followUser == null) {
			throw new BusinessException("Actualmente no estás siguiendo a este usuario");
		}else {
			if(followUser.getState()==1) {
				result.setMessage("Has dejado correctamente de seguir al usuario "+data.getFollowingUserName());
			}else {
				result.setMessage("Has cancelado correctamente la solicitud para seguir al usuario "+data.getFollowingUserName());
			}
			
		}
		repo.stopFollowingUser(data.getUsername(), data.getFollowingUserName());

		return result;
	}


	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
