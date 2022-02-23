package com.example.creditcomputator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.creditcomputator.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String>{
	
	
}
