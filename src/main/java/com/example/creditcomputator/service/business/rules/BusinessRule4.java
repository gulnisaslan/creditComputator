package com.example.creditcomputator.service.business.rules;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.creditcomputator.entity.CreditAppeal;


@Service
public class BusinessRule4 implements BusinessRule{


	@Override
	public Optional<CreditAppeal> decideCreditLimit(BusinessRuleData businessRuleData) {
		
		CreditAppeal creditAppeal = null;
		
		if( businessRuleData.getCreditScore() >= 1000 ) {
			
			final int creditLimitFactor = 4;
			double creditLimit = businessRuleData.getMonthlyIncome() * creditLimitFactor;
			
			creditAppeal = new CreditAppeal();
			creditAppeal.setCreditLimit(creditLimit);
			creditAppeal.setCreditApprovalStatus(true);
			creditAppeal.setCreatedAt(LocalDateTime.now());
			
		}
		
		return Optional.ofNullable(creditAppeal);
	}
	

	

}
