package com.example.creditcomputator.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AppUserRequest {

	private String nationalId;

	private String firstName;

	private String lastName;

	private Double monthlyIncome;

	private String phoneNumber;
	
	
}
