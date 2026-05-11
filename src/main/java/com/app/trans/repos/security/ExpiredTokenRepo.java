package com.app.trans.repos.security;

import com.app.trans.models.ExpiredTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ExpiredTokenRepo extends JpaRepository<ExpiredTokens, Long> {
    // Find a blacklisted token by email and issued at time (iat)
    Optional<ExpiredTokens> findByEmail(String email);
}