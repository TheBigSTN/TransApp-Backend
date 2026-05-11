package com.app.trans.repos;

import com.app.trans.models.Alimentare;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentareRepo extends JpaRepository<Alimentare, Long> {
	
	 @Query("SELECT c FROM Alimentare c WHERE c.data_alimentare BETWEEN :startDate AND :endDate AND c.company.id = :companyId")
	 List<Alimentare> findAllByPeriod(LocalDate startDate, LocalDate endDate, UUID companyId);

	 Optional<Alimentare> findByIdAndCompanyId(long id, UUID companyId);

	 List<Alimentare> findAllByCompanyId(UUID companyId);
}
