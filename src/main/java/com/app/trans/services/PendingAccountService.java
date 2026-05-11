package com.app.trans.services;

import com.app.trans.dtos.CreateAccountRequest;
import com.app.trans.models.Company;
import com.app.trans.models.PendingAccount;
import com.app.trans.repos.PendingAccountsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PendingAccountService {

    private final PendingAccountsRepo pendingAccountsRepo;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder =
            Base64.getUrlEncoder().withoutPadding();

    public String createNewPendingAccount(CreateAccountRequest request, Company company) {
        PendingAccount pendingAccount = PendingAccount.builder()
                .activationCode(generateActivationCode())
                .role(request.getRole())
                .company(company)
                .expiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                .build();

        pendingAccountsRepo.save(pendingAccount);
        return pendingAccount.getActivationCode();
    }

    public PendingAccount getPendingAccount(String activationCode) {

        PendingAccount pending = pendingAccountsRepo
                .findByActivationCode(activationCode)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid activation code"));

        if (pending.getExpiresAt() != null &&
                pending.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Activation code expired");
        }

        return pending;
    }

    public List<PendingAccount> getPendingAccounts(UUID companyId) {
        return pendingAccountsRepo.findByCompanyId(companyId);
    }

    @Transactional
    public void activatePendingAccount(String activationCode) {

        PendingAccount pending = getPendingAccount(activationCode);

        // activarea este consumarea tokenului
        pendingAccountsRepo.delete(pending);
    }

    private String generateActivationCode() {
        byte[] randomBytes = new byte[32]; // 256 bits
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
