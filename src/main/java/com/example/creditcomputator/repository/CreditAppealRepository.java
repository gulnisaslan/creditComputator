package com.example.creditcomputator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.creditcomputator.entity.CreditAppeal;

public interface CreditAppealRepository extends JpaRepository<CreditAppeal, String>{

	List<CreditAppeal> findByAppUser_NationalId(String appUserNationalId);
	
}
