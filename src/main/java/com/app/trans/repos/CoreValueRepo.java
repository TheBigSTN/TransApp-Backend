package com.app.trans.repos;

import com.app.trans.models.CoreValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoreValueRepo extends JpaRepository<CoreValue, UUID> {
    List<CoreValue> findByCompanyIdOrderByOrderAsc(UUID companyId);
//    void deleteByCompanyId(UUID companyId);
}
