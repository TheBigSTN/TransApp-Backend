package com.app.trans.repos;

import com.app.trans.models.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyInfoRepo extends JpaRepository<CompanyInfo, UUID> {
    Optional<CompanyInfo> findByCompanyId(UUID companyId);
}
