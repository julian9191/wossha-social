package com.wossha.social.infrastructure.filters;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UsernameInfoAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private String firstName;
	private String lastName;
	private String profilePicture;

	public UsernameInfoAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		// TODO Auto-generated constructor stub
	}

	public UsernameInfoAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, String firstName, String lastName,
			String profilePicture) {
		super(principal, credentials, authorities);
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePicture = profilePicture;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

}
