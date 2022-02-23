package com.example.creditcomputator.service.business.rules;

import java.util.Optional;

import com.example.creditcomputator.entity.CreditAppeal;

public interface BusinessRule {
	
	Optional<CreditAppeal> decideCreditLimit(BusinessRuleData businessRuleData);

}
