package com.example.creditcomputator.service;

import java.util.List;

import com.example.creditcomputator.dto.request.CreditScoreRequest;
import com.example.creditcomputator.dto.response.CreditScoreResponse;

public interface CreditScoreService {

	List<CreditScoreResponse> getAllCreditScores();

	List<CreditScoreResponse> getCreditScoresByPagination(int page, int size);
	
	CreditScoreResponse getCreditScoreByAppUserNationalId(String appUserNationalId);

	CreditScoreResponse getCreditScoreById(String id);
	
	Boolean isCreditScorePresent(String id);

	CreditScoreResponse assignCreditScore(CreditScoreRequest creditScoreRequest);
	
	CreditScoreResponse updateCreditScoreByAppUserNationalId(CreditScoreRequest creditScoreRequest);

	CreditScoreResponse deleteCreditScoreById(String id);

	
}
