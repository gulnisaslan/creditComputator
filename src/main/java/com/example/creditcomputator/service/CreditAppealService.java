package com.example.creditcomputator.service;

import java.util.List;

import com.example.creditcomputator.dto.response.CreditAppealResponse;

public interface CreditAppealService {
	
	List<CreditAppealResponse> getCreditAppealsByAppUserNationalId(String appUserNationalId);

	CreditAppealResponse deleteCreditAppealById(String id);
	
	CreditAppealResponse appealForCredit(String appUserNationalId);
	
	Boolean isCreditAppealPresent(String id);

}
