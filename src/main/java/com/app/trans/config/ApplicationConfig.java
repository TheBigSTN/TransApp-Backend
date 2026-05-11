package com.app.trans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.trans.repos.UserRepo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserRepo userRepo;

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found"));
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Bean
	// public OpenAPI openAPI() {
	// return new OpenAPI().addSecurityItem(new SecurityRequirement().
	// addList("Bearer Authentication"))
	// .components(new Components().addSecuritySchemes
	// ("Bearer Authentication", createAPIKeyScheme()))
	// .info(new Info().title("My REST API")
	// .description("Some custom description of API.")
	// .version("1.0").contact(new Contact().name("Sallo Szrajbman")
	// .email( "www.baeldung.com").url("salloszraj@gmail.com"))
	// .license(new License().name("License of API")
	// .url("API license URL")));
	// }
	//
	// private SecurityScheme createAPIKeyScheme() {
	// return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	// .bearerFormat("JWT")
	// .scheme("bearer");
	// }

}
