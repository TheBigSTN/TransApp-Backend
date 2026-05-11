package com.app.trans.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.trans.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
}
