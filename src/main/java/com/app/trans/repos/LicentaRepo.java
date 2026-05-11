package com.app.trans.repos;

import com.app.trans.models.Licenta;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LicentaRepo extends JpaRepository<Licenta, Long> {

	 @Query("SELECT c FROM Licenta c WHERE c.data_inceput BETWEEN :startDate AND :endDate "
	 		+ "OR c.data_final BETWEEN :startDate AND :endDate")
	 List<Licenta> findAllByPeriod(LocalDate startDate, LocalDate endDate);
}
