package com.app.trans.repos;

import com.app.trans.models.PendingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PendingAccountsRepo extends JpaRepository<PendingAccount, UUID> {

    Optional<PendingAccount> findByActivationCode (String activationCode);

    List<PendingAccount> findByCompanyId(UUID companyId);
}
