package com.app.trans.security;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import com.app.trans.models.Role;
import com.app.trans.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.app.trans.models.ExpiredTokens;
import com.app.trans.services.TokenExpireService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
@RequiredArgsConstructor
public class JwtService {

	@SuppressWarnings("unused")
	@Value("${application.security.jwt.secret-key}")
	private String secretKey;

	@SuppressWarnings("unused")
	@Value("${application.security.jwt.expiration}")
	private long jwtExpiration;

	@SuppressWarnings("unused")
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;

	private final TokenExpireService tokenExpireService;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Role extractRole(String token) {
		String roleString = extractClaimByName(token, "role");

		if (roleString == null) {
			throw new IllegalStateException("Role not present in token");
		}

		if (roleString.startsWith("ROLE_")) {
			roleString = roleString.substring(5);
		}

		return Role.valueOf(roleString);
	}

	public UUID extractCompanyId(String token) {
        String companyIdString = extractClaimByName(token, "companyId");
		if (companyIdString == null) {
			throw new IllegalStateException("Company ID not present in token");
		}
		return UUID.fromString(companyIdString);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractClaimByName(String token, String claimName) {
		return extractClaim(token, claims -> claims.get(claimName, String.class));
	}

	public String generateToken(User userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(
			Map<String, Object> extraClaims,
			User userDetails) {
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}

	public long extractIssuedAt(String token) {
		return extractClaim(token, Claims::getIssuedAt).getTime() / 1000;
	}

//	public String generateRefreshToken(
//			User userDetails) {
//		return buildToken(new HashMap<>(), userDetails, refreshExpiration);
//	}

	private String buildToken(
			Map<String, Object> extraClaims,
			User user,
			long expiration) {

		String role = user.getRole().toString();

		extraClaims.put("role", role);
		extraClaims.put("companyId", user.getCompany().getId().toString());

		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		try {
			final String username = extractUsername(token);

			// Ensure the username is not null and matches the user details
			if (username == null || !username.equals(userDetails.getUsername())) {
				return false;
			}

			// Convert the secret key string to a Key object
			Key key = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "HmacSHA256");

			// Validate the token signature and check expiration
			Jwts.parserBuilder()
					.setSigningKey(key) // Use the Key object instead of a String
					.build()
					.parseClaimsJws(token); // Will throw an exception if the signature is invalid or token is expired

			return true; // Token is valid
		} catch (JwtException | IllegalArgumentException e) {
			return false; // Token is invalid or expired
		}
	}

	public boolean isTokenExpired(String token) {
		// Get expiration date from the token
		Date expirationDate = extractExpiration(token);

		// Check if the token is blacklisted
		Optional<ExpiredTokens> expiredToken = tokenExpireService.findByEmail(extractUsername(token));

		if (expiredToken.isPresent()) {
			long expiredIat = expiredToken.get().getIat(); // Retrieve the 'iat' field from the expired token
			long tokenIat = extractIssuedAt(token); // Extract the 'iat' value from the token

			// If the token was issued after the expired token's 'iat', it is not valid
			if (tokenIat <= expiredIat) {
				return true; // Token is considered expired/invalid
			}
		}

		// Default expiration check
		return expirationDate.before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String getTokenFromHeader (String header) {
		return header.substring(7);
	}

}
