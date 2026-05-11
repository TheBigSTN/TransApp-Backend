package com.app.trans.security.auth;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.app.trans.dtos.ActivateAccountRequest;
import com.app.trans.dtos.CreateAccountRequest;
import com.app.trans.models.*;
import com.app.trans.models.enums.Role;
import com.app.trans.services.CompanyDataService;
import com.app.trans.services.PendingAccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.trans.repos.UserRepo;
import com.app.trans.security.JwtService;
import com.app.trans.services.TokenExpireService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepo repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final TokenExpireService tokenExpireService;
	private final PendingAccountService pendingAccountService;
	private final CompanyDataService companyDataService;

	public AuthenticationResponse register(RegisterRequest request) {
		repository.findByEmail(request.getEmail()).ifPresent(u -> {
			throw new IllegalStateException("Email already exists");
		});

		Company company = companyDataService.createCompany(
				request.getCompanyName(),
				request.getCompanyDescription());
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.company(company)
				.role(Role.MANAGER)
				.build();
		repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	@Transactional
	public String createAccount(CreateAccountRequest request, String token) throws AccessDeniedException {
		Role roleFromToken = jwtService.extractRole(token);
		UUID companyId = jwtService.extractCompanyId(token);
		Company company = companyDataService.getCompanyById(companyId);

		if (roleFromToken != Role.MANAGER) {
			throw new AccessDeniedException("Only MANAGER can create accounts");
		}

        return pendingAccountService.createNewPendingAccount(request, company);
	}

	@Transactional
	public List<PendingAccount> getPendingAccounts(String token) throws AccessDeniedException {
		Role roleFromToken = jwtService.extractRole(token);
		UUID companyId = jwtService.extractCompanyId(token);
		Company company = companyDataService.getCompanyById(companyId);

		if (roleFromToken != Role.MANAGER) {
			throw new AccessDeniedException("Only MANAGER can get pending accounts");
		}

		return pendingAccountService.getPendingAccounts(company.getId());
	}

	@Transactional
	public AuthenticationResponse activateAccount(ActivateAccountRequest request) {
		PendingAccount pending = pendingAccountService.getPendingAccount(request.getActivationCode());
		pendingAccountService.activatePendingAccount(request.getActivationCode());

		repository.findByEmail(request.getEmail()).ifPresent(u -> {
			throw new IllegalStateException("Email already exists");
		});

		User user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.company(pending.getCompany())
				.role(pending.getRole())
				.build();

		repository.save(user);

		String jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()));

		var user = repository.findByEmail(request.getEmail())
				.orElseThrow();
		var jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public PasswordResetResponse resetPassword(PasswordResetRequest request, String token) {
		try {
			String email = jwtService.extractUsername(token);
			// Retrieve the user by email
			User user = repository.findByEmail(email)
					.orElseThrow(() -> new RuntimeException("User not found"));

			// Check if the current password matches the stored password
			if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
				throw new BadCredentialsException("Current password is incorrect");
			}

			// Ensure the new password is different from the current password
			if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
				throw new RuntimeException("New password must be different from the current password");
			}

			// Set the new password
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
			repository.save(user);

			tokenExpireService.blacklistToken(email);

			return PasswordResetResponse.builder()
					.message("Password reset successfully")
					.success(true)
					.timestamp(LocalDateTime.now().toString())
					.build();
		} catch (Exception e) {
			System.err.println("Password reset failed: " + e.getMessage());
			return PasswordResetResponse.builder()
					.message("Password reset failed: " + e.getMessage())
					.success(false)
					.timestamp(LocalDateTime.now().toString())
					.build();
		}
	}

	public User getUserByEmail(String email) {
		return repository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}

}