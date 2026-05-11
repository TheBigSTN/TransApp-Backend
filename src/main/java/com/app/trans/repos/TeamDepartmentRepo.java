package com.app.trans.repos;

import com.app.trans.models.TeamDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TeamDepartmentRepo extends JpaRepository<TeamDepartment, UUID> {
    List<TeamDepartment> findByCompanyIdOrderByOrderAsc(UUID companyId);
//    void deleteByCompanyId(UUID companyId);
}
