package com.app.trans.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.trans.config.SecurityConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer "))
			return true;

		final String jwt;
		final String username;

		jwt = authHeader.substring(7); // Extract the token
		username = jwtService.extractUsername(jwt); // Extract username from token

		if (username == null || SecurityContextHolder.getContext().getAuthentication() != null)
			return true;

		return Arrays.stream(SecurityConfig.WHITE_LIST_URL)
				.anyMatch(pattern -> new AntPathRequestMatcher(pattern).matches(request));
	}

	@Override
	protected void doFilterInternal(
			@NotNull HttpServletRequest request,
			@NotNull HttpServletResponse response,
			@NotNull FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization"); // Get the token from the request's header
		final String jwt;
		final String username;

		jwt = authHeader.substring(7); // Extract the token
		username = jwtService.extractUsername(jwt); // Extract username from token

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

		// Check if token is invalid
		if (!jwtService.isTokenValid(jwt, userDetails)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
			response.setContentType("application/json");
			response.getWriter()
					.write("{\"status\": \"401\", \"message\": \"Token expired and is invalid. Please log in again.\"}");
			return;
		}

		// Check if token is expired
		if (jwtService.isTokenExpired(jwt)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
			response.setContentType("application/json");
			response.getWriter().write(
					"{\"status\": \"401\", \"reason\": \"expired\", \"message\": \"Token expired. Please log in again.\"}");
			return;
		}

		// Token is valid, set authentication and continue the filter chain
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities());
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}

}