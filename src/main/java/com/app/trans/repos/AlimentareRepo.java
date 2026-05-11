package com.app.trans.repos;

import com.app.trans.models.Alimentare;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentareRepo extends JpaRepository<Alimentare, Long> {
	
	 @Query("SELECT c FROM Alimentare c WHERE c.data_alimentare BETWEEN :startDate AND :endDate")
	 List<Alimentare> findAllByPeriod(LocalDate startDate, LocalDate endDate);
}
