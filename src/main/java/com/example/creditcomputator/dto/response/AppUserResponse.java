package com.example.creditcomputator.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AppUserResponse {

	private String nationalId;

	private String fullName;

	private Double monthlyIncome;

	private String phoneNumber;
	
	private LocalDateTime createdAt;
	
	private Double creditScore;
	
	private LocalDateTime creditScoreAssignedAt;
	
	private List<String> creditAppealIds;

	
}
