package com.app.trans.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	// @JsonProperty("access_token") Atributul token este marcat cu
	// @JsonProperty("access_token"), indicând că acesta va fi serializat sub numele
	// access_token atunci când răspunsul este transformat în format JSON.
	private String token;
	// private Role role;

	// @JsonProperty("refresh_token")
	// private String refreshToken;
}