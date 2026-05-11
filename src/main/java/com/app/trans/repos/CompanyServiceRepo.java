package com.app.trans.repos;

import com.app.trans.models.CompanyService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyServiceRepo extends JpaRepository<CompanyService, UUID> {
    List<CompanyService> findByCompanyIdOrderByOrderAsc(UUID companyId);
//    void deleteByCompanyId(UUID companyId);
}
