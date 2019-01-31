package com.wossha.social.infrastructure.services;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;;

@Component
public class JWTService {
public static final String SECRET = Base64Utils.encodeToString("Alguna.Clave.Secreta.123456".getBytes());
	
	public static final long EXPIRATION_DATE = 14000000L;//3600000*4
	//public static final long EXPIRATION_DATE = 30000L;//3600000/120
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String PARAM_STRING = "token";
	public static final String FIRST_NAME_PARAM = "firstName";
	public static final String LAST_NAME_PARAM = "lastName";
	public static final String PROFILE_PICTURE_PARAM = "profilePicture";
	
	public String create(Authentication auth) throws IOException {

		String username = ((User) auth.getPrincipal()).getUsername();

		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder().setClaims(claims).setSubject(username)
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)).compact();

		return token;
	}

	
	public boolean validate(String token) {

		try {

			getClaims(token);

			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}

	}

	
	public Claims getClaims(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes())
				.parseClaimsJws(resolve(token)).getBody();
		return claims;
	}

	
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	public String getClaim(String token, String key) {
		return (String) getClaims(token).get(key);
	}
	

	
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("authorities");
		
		
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleWosshaGrantedAuthorityMixin.class)
						.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
		
		return authorities;
	}

	
	public String resolve(String token) {
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			return token.replace(TOKEN_PREFIX, "");
		}
		return null;
	}

}

