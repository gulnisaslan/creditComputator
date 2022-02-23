package com.example.creditcomputator.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.creditcomputator.service.business.rules.BusinessRule;

@Configuration
public class BusinessRuleConfig {

	@Bean(name = "creditScoreRules")
	public List<BusinessRule> getBusinessRules(List<BusinessRule> rules){
		return rules;
	}
	
}
