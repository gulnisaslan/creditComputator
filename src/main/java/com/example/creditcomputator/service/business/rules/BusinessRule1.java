package com.example.creditcomputator.service.business.rules;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.creditcomputator.entity.CreditAppeal;


@Service
public class BusinessRule1 implements BusinessRule {

	@Override
	public Optional<CreditAppeal> decideCreditLimit(BusinessRuleData businessRuleData) {
		
		CreditAppeal creditAppeal = null;
		
		if( businessRuleData.getCreditScore() < 500 ) {
			creditAppeal = new CreditAppeal();
			
			creditAppeal.setCreditApprovalStatus(false);
			creditAppeal.setCreditLimit(null);
			creditAppeal.setCreatedAt(LocalDateTime.now());
		}
		
		return Optional.ofNullable(creditAppeal);
	}
	

	

}
