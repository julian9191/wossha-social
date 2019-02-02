package com.wossha.social.commands.refuseFollow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.commands.refuseFollow.model.RefuseFollow;
import com.wossha.social.infrastructure.repositories.SocialRepository;

@Component
public class RefuseFollowCommand implements ICommand<RefuseFollow> {

	private RefuseFollow data;
	private String username;

	@Autowired
	private SocialRepository repo;
	

	@Override
	public String commandName() {
		return "FollowUser";
	}

	@Override
	public RefuseFollow data() {
		return this.data;
	}

	@Override
	public void setData(RefuseFollow data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();

		repo.deleteNotification(data.getSenderUsername(), data.getUsername(), data.getNotificationType());
		result.setMessage("La solicitud de  @"+data.getSenderUsername()+" se ha eliminado");
		
		return result;
	}
	

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
