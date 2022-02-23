package com.example.creditcomputator.service.business.rules;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.creditcomputator.entity.CreditAppeal;


@Service
public class BusinessRule2 implements BusinessRule{

	@Override
	public Optional<CreditAppeal> decideCreditLimit(BusinessRuleData businessRuleData) {
		
		CreditAppeal creditAppeal = null;
		
		if( businessRuleData.getCreditScore() >= 500 && businessRuleData.getCreditScore() < 1000 
				&& businessRuleData.getMonthlyIncome() < 5000) {
			creditAppeal = new CreditAppeal();
			
			creditAppeal.setCreditLimit(10_000.0);
			creditAppeal.setCreditApprovalStatus(true);
			creditAppeal.setCreatedAt(LocalDateTime.now());
			
			
		}
		
		
		return Optional.ofNullable(creditAppeal);
	}
	

	

}
