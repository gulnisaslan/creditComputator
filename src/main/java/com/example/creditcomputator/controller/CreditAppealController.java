package com.example.creditcomputator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.creditcomputator.dto.response.CreditAppealResponse;
import com.example.creditcomputator.service.CreditAppealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestScope
@RequestMapping("/api/creditappeals")
@CrossOrigin
@Api(tags = "Credit Appeals")
public class CreditAppealController {
	
	private final CreditAppealService creditAppealService;

	public CreditAppealController(CreditAppealService creditAppealService) {
		this.creditAppealService = creditAppealService;
	}

	
	@GetMapping("/getCreditAppealsByAppUserNationalId")
	@ApiOperation(value = "This method is used to get the credit appeals based on the app user national id")
	List<CreditAppealResponse> getCreditAppealsByAppUserNationalId(@RequestParam String appUserNationalId){
		return creditAppealService.getCreditAppealsByAppUserNationalId(appUserNationalId);
	}
	
	@DeleteMapping("/deleteCreditAppealById")
	CreditAppealResponse deleteCreditAppealById(@RequestParam String id) {
		return creditAppealService.deleteCreditAppealById(id);
	}
	
	@PostMapping("/appealForCredit")
	CreditAppealResponse appealForCredit(@RequestParam String appUserNationalId) {
		return creditAppealService.appealForCredit(appUserNationalId);
	}
	
	
}
