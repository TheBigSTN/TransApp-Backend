package com.app.trans.repos;

import com.app.trans.models.Masina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasinaRepo extends JpaRepository<Masina, Long> {
}
