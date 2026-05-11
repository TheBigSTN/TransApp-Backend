package com.app.trans.repos;

import com.app.trans.models.Sofer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoferRepo extends JpaRepository<Sofer, Long> {

    List<Sofer> findAllByCompanyId(UUID companyId);

    Optional<Sofer> findByIdAndCompanyId(Long id, UUID companyId);
}
