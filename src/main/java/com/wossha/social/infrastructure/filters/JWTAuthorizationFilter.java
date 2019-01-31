package com.wossha.social.infrastructure.filters;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.wossha.social.infrastructure.services.JWTService;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTService jwtService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(JWTService.HEADER_STRING);

		if (header == null) {
			header = request.getParameter(JWTService.PARAM_STRING);
		}

		if (!requiresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}

		UsernameInfoAuthenticationToken authentication = null;
		if (jwtService.validate(header)) {
			authentication = new UsernameInfoAuthenticationToken(jwtService.getUsername(header), null,
					jwtService.getRoles(header),
					URLDecoder.decode(jwtService.getClaim(header, JWTService.FIRST_NAME_PARAM), "UTF-8"),
					URLDecoder.decode(jwtService.getClaim(header, JWTService.LAST_NAME_PARAM), "UTF-8"),
					jwtService.getClaim(header, JWTService.PROFILE_PICTURE_PARAM));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);

	}

	protected boolean requiresAuthentication(String header) {

		if (header == null || !header.startsWith(JWTService.TOKEN_PREFIX)) {
			return false;
		}
		return true;
	}

}
