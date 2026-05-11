package com.app.trans.repos;

import com.app.trans.models.Masina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MasinaRepo extends JpaRepository<Masina, Long> {

    List<Masina> findAllByCompanyId(UUID companyId);

    Optional<Masina> findByIdAndCompanyId(Long id, UUID companyId);
}
