package com.app.trans.repos;

import com.app.trans.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Optional<Client> findByIdAndCompanyId(long Id, UUID companyId);

    List<Client> findAllByCompanyId(UUID companyId);
}
