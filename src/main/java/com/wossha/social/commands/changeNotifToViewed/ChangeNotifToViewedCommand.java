package com.wossha.social.commands.changeNotifToViewed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;
import com.wossha.social.commands.changeNotifToViewed.model.ChangeNotifToViewed;
import com.wossha.social.infrastructure.filters.UsernameInfoAuthenticationToken;
import com.wossha.social.infrastructure.repositories.SocialRepository;

@Component
public class ChangeNotifToViewedCommand implements ICommand<ChangeNotifToViewed> {

	private ChangeNotifToViewed data;
	private String username;

	@Autowired
	private SocialRepository repo;
	

	@Override
	public String commandName() {
		return "ChangeNotifToViewed";
	}

	@Override
	public ChangeNotifToViewed data() {
		return this.data;
	}

	@Override
	public void setData(ChangeNotifToViewed data) {
		this.data = data;
	}

	@Override
	public CommandResult execute() throws BusinessException, TechnicalException {
		CommandResult result = new CommandResult();

		UsernameInfoAuthenticationToken auth = (UsernameInfoAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		repo.changeNotifToViewed(auth.getPrincipal().toString(), data.getIds());
		
		result.setMessage("Las notificaciones se han actualizado correctamente");

		return result;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
