package com.example.creditcomputator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.creditcomputator.entity.CreditScore;

public interface CreditScoreRepository extends JpaRepository<CreditScore, String>{

	Optional<CreditScore> findByAppUser_NationalId(String appUserNationalId);
	
}
