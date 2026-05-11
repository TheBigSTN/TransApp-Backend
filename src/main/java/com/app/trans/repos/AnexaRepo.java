package com.app.trans.repos;


import com.app.trans.models.Anexa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnexaRepo extends JpaRepository<Anexa, Long> {
}
