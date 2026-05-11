package com.app.trans.repos;

import com.app.trans.models.Cursa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CursaRepo extends JpaRepository<Cursa, Long> {

	 @Query("SELECT c FROM Cursa c WHERE c.dataEfectuare BETWEEN :startDate AND :endDate")
	 List<Cursa> findAllByPeriod(LocalDate startDate, LocalDate endDate);

}
