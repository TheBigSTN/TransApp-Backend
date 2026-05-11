package com.app.trans.repos;

import com.app.trans.models.Sofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoferRepo extends JpaRepository<Sofer, Long> {
}
