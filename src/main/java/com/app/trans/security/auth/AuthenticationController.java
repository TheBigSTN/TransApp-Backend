package com.app.trans.security.auth;

import com.app.trans.dtos.ActivateAccountRequest;
import com.app.trans.dtos.CreateAccountRequest;
import com.app.trans.dtos.PendingAccountDTO;
import com.app.trans.models.*;
import com.app.trans.services.PendingAccountService;
import lombok.RequiredArgsConstructor;

import com.app.trans.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final JwtService jwtService;
  private final AuthenticationService service;
//  private final UserDetailsService userDetailsService;
//  private final PendingAccountService pendingAccountService;

  @PostMapping("/account/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/account/create")
  public ResponseEntity<String> createAccount(
          @RequestBody CreateAccountRequest request,
          @RequestHeader("Authorization") String authHeader
  ) {
    String token = jwtService.getTokenFromHeader(authHeader);
      try {
          return ResponseEntity.ok(service.createAccount(request, token));
      } catch (AccessDeniedException e) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
  }

  @PostMapping("/account/activate")
  public ResponseEntity<AuthenticationResponse> activateAccount(
          @RequestBody ActivateAccountRequest request
  ) {

    AuthenticationResponse auth = service.activateAccount(request);

    return ResponseEntity.ok(auth);
  }

  @GetMapping("/account/pending")
  public ResponseEntity<List<PendingAccountDTO>> pendingAccount(
          @RequestHeader("Authorization") String authHeader
  ) {
    String token = jwtService.getTokenFromHeader(authHeader);
    try {
      return ResponseEntity.ok(service.getPendingAccounts(token).stream().map(PendingAccount::toDTO).toList());
    } catch (AccessDeniedException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<PasswordResetResponse> resetPassword(
      @RequestBody PasswordResetRequest request,
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.substring(7);
    return ResponseEntity.ok(service.resetPassword(request, token));
  }

  @GetMapping("/user")
  public ResponseEntity<User> getUserByEmail(HttpServletRequest request) {
    // Extrage token-ul din header-ul Authorization
    String authHeader = request.getHeader("Authorization");
    String jwt;
    String email = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7); // Extrage token-ul din header
      email = jwtService.extractUsername(jwt); // Aici extragi email-ul (sau username-ul)
    }

    if (email != null) {
      // Căutăm utilizatorul în baza de date folosind email-ul extras din token
      User user = service.getUserByEmail(email);
      return ResponseEntity.ok(user);
    }
    // Dacă nu există token sau nu se poate extrage email-ul
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

  }

  @GetMapping("/is-expired")
  public ResponseEntity<Boolean> isExpired(
      @RequestHeader("Authorization") String authHeader) {
    if (authHeader.isEmpty() || authHeader.startsWith("Bearer "))
      return ResponseEntity.ok(true);
    String jwt = authHeader.substring(7);
    return ResponseEntity.ok(jwtService.isTokenExpired(jwt));
  }

  // @PostMapping("/forgot-password")
  // public ResponseEntity<?> forgotPassword(
  // @RequestParam String email
  // ) {
  // forgetPasswordService.processForgotPassword(email);
  // return ResponseEntity.ok("Email sent successfully");
  // }
  //
  // @PostMapping("/reset-password")
  // public ResponseEntity<?> resetPassword(
  // @RequestBody ResetPasswordRequest resetPasswordRequest
  // ) {
  // forgetPasswordService.processResetPassword(resetPasswordRequest);
  // return ResponseEntity.ok("Password reset successfully");
  // }
  // @PostMapping("/refresh-token")
  // public void refreshToken(
  // HttpServletRequest request,
  // HttpServletResponse response
  // ) throws IOException {
  // service.refreshToken(request, response);
  // }

}