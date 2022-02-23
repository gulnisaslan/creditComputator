package com.example.creditcomputator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.creditcomputator.dto.request.CreditScoreRequest;
import com.example.creditcomputator.dto.response.CreditScoreResponse;
import com.example.creditcomputator.service.CreditScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestScope
@RequestMapping("/api/creditscores")
@CrossOrigin
@Api(tags = "Credit Scores")
public class CreditScoreController {

	private final CreditScoreService creditScoreService;

	public CreditScoreController(CreditScoreService creditScoreService) {
		this.creditScoreService = creditScoreService;
	}
	
	
	@GetMapping("/getAllCreditScores")
	List<CreditScoreResponse> getAllCreditScores(){
		return creditScoreService.getAllCreditScores();
	}

	@GetMapping("/getCreditScoresByPagination")
	List<CreditScoreResponse> getCreditScoresByPagination(@RequestParam int page, @RequestParam int size){
		return creditScoreService.getCreditScoresByPagination(page, size);
	}
	
	
	@GetMapping("/getCreditScoreByAppUserId")
	@ApiOperation(value = "This method is used to get the credit score based on the app user national id")
	CreditScoreResponse getCreditScoreByAppUserNationalId(@RequestParam String appUserNationalId) {
		return creditScoreService.getCreditScoreByAppUserNationalId(appUserNationalId);
	}

	@GetMapping("/getCreditScoreById")
	CreditScoreResponse getCreditScoreById(@RequestParam String id) {
		return creditScoreService.getCreditScoreById(id);
	}

	@PostMapping("/assignCreditScore")
	CreditScoreResponse assignCreditScore(@RequestBody CreditScoreRequest creditScoreRequest) {
		return creditScoreService.assignCreditScore(creditScoreRequest);
	}
	
	@PutMapping("/updateCreditScoreByAppUserNationalId")
	CreditScoreResponse updateCreditScoreByAppUserNationalId(@RequestBody CreditScoreRequest creditScoreRequest) {
		return creditScoreService.updateCreditScoreByAppUserNationalId(creditScoreRequest);
	}

	@DeleteMapping("/deleteCreditScoreById")
	CreditScoreResponse deleteCreditScoreById(@RequestParam String id) {
		return creditScoreService.deleteCreditScoreById(id);
	}
	
	
	
}
