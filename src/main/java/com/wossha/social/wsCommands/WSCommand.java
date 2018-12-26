package com.wossha.social.wsCommands;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import com.wossha.msbase.commands.CommandResult;
import com.wossha.msbase.commands.ICommand;
import com.wossha.msbase.exceptions.BusinessException;
import com.wossha.msbase.exceptions.TechnicalException;

public abstract class WSCommand<T> implements ICommand<T> {
	public abstract String commandName();
    public abstract void setData(T data);
    public abstract void setUsername(String username); 
    public abstract void setHeaderAccessor(SimpMessageHeaderAccessor headerAccessor); 
    public abstract void setMessagingTemplate(SimpMessageSendingOperations messagingTemplate);
    public abstract CommandResult execute() throws BusinessException, TechnicalException;
}
