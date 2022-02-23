package com.example.creditcomputator.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreditScoreResponse {

	private String id;

	private Double assignedCreditScore;

	private String appUserFullName;

	private Double appUserMonthlyIncome;

	private String appUserPhoneNumber;

	private LocalDateTime createdAt;

	
}
