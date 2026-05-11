package com.app.trans.repos;

import com.app.trans.models.Licenta;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LicentaRepo extends JpaRepository<Licenta, Long> {

	List<Licenta> findAllByCompanyId(UUID companyId);

	Optional<Licenta> findByIdAndCompanyId(Long id, UUID companyId);

	@Query("SELECT c FROM Licenta c WHERE (c.data_inceput BETWEEN :startDate AND :endDate "
			+ "OR c.data_final BETWEEN :startDate AND :endDate) AND c.company.id = :companyId")
	List<Licenta> findAllByPeriodAndCompanyId(LocalDate startDate, LocalDate endDate, UUID companyId);
}
