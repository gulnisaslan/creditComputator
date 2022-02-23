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
public class CreditAppealResponse {

	private String id;

	private Double assignedCreditLimit;

	private Boolean creditApprovalStatus;

	private LocalDateTime createdAt;
	
	private String appUserNationalId;
	

}
