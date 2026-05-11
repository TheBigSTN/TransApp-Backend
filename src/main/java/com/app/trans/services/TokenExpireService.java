package com.app.trans.services;

import com.app.trans.models.ExpiredTokens;
import com.app.trans.repos.security.ExpiredTokenRepo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenExpireService {

    private final ExpiredTokenRepo expiredTokenRepo;

    // Method to blacklist a token
    public void blacklistToken(String email) {
        Optional<ExpiredTokens> existingTokenOpt = expiredTokenRepo.findByEmail(email);

        // Delete the existing blacklisted token if it exists
        existingTokenOpt.ifPresent(expiredTokenRepo::delete);
        long blacklistedAt = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC); // Current time in seconds
        ExpiredTokens blacklistedToken = new ExpiredTokens(email, blacklistedAt);
        expiredTokenRepo.save(blacklistedToken); // Save to database
    }

    public Optional<ExpiredTokens> findByEmail(String email) {
        return expiredTokenRepo.findByEmail(email);
    }
}
