package com.app.trans.repos;

import com.app.trans.models.Cursa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CursaRepo extends JpaRepository<Cursa, Long> {

	List<Cursa> findAllByCompanyId(UUID companyId);

	Optional<Cursa> findByIdAndCompanyId(Long id, UUID companyId);

	@Query("SELECT c FROM Cursa c WHERE c.dataEfectuare BETWEEN :startDate AND :endDate AND c.company.id = :companyId")
	List<Cursa> findAllByPeriodAndCompanyId(LocalDate startDate, LocalDate endDate, UUID companyId);

}
