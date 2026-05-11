package com.app.trans.repos;

import com.app.trans.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, UUID> {
//    Company findByName(String name);
//    List<Company> findAll();
}

