package com.example.creditcomputator.service.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.creditcomputator.entity.CreditAppeal;
import com.example.creditcomputator.service.business.rules.BusinessRule;
import com.example.creditcomputator.service.business.rules.BusinessRuleData;

@Service
public class BusinessRuleService {

	@Autowired
	private List<BusinessRule> businessRules;
	
	public Optional<CreditAppeal> getCreditAppeal(BusinessRuleData businessRuleData) {
		
		return businessRules.stream()
						.map( businessRule ->  businessRule.decideCreditLimit(businessRuleData) )
						.filter( Optional::isPresent )
						.map( Optional::get )
						.findFirst();
	}
	
	
}
