package com.app.trans.repos;


import com.app.trans.models.Anexa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnexaRepo extends JpaRepository<Anexa, Long> {
    Optional<Anexa> findByIdAndCompanyId(long id, UUID companyId);

    List<Anexa> findAllByCompanyId(UUID companyId);
}
