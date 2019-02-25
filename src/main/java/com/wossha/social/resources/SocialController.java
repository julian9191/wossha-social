package com.wossha.social.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import com.wossha.msbase.controllers.ControllerWrapper;
import com.wossha.social.dto.FollowingUser;
import com.wossha.social.dto.Notification;
import com.wossha.social.infrastructure.filters.UsernameInfoAuthenticationToken;
import com.wossha.social.infrastructure.repositories.SocialRepository;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping(value = "/social")
public class SocialController extends ControllerWrapper {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SocialRepository repo;

	@GetMapping(value = "/following-users")
	public @ResponseBody List<FollowingUser> getFollowingUsers() {
		UsernameInfoAuthenticationToken auth = (UsernameInfoAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString();
		
		List<FollowingUser> c = repo.getFollowingUsers(username);
		return c;
	}
	
	@GetMapping(value = "/message-history")
	public @ResponseBody Map<String, Object> getChatMessageHistory(@RequestParam("init") int init, @RequestParam("limit") int limit) {
		UsernameInfoAuthenticationToken auth = (UsernameInfoAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString();
		
		Map<String, Object> c = repo.getChatMessageHistory(username, init, limit);
		return c;
	}
	
	@GetMapping(value = "/notifications")
	public @ResponseBody List<Notification> getUserNotifications() {
		UsernameInfoAuthenticationToken auth = (UsernameInfoAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString();
		
		List<Notification> c = repo.getUserNotifications(username);
		return c;
	}
	
	@GetMapping(value = "/posts")
	public @ResponseBody Map<String, Object> getPosts(@RequestParam("init") int init, @RequestParam("limit") int limit, @RequestParam("username") String profileUsername) {
		UsernameInfoAuthenticationToken auth = (UsernameInfoAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString();
		
		Map<String, Object> c = repo.getPosts(username, init, limit, profileUsername);
		return c;
	}

}
