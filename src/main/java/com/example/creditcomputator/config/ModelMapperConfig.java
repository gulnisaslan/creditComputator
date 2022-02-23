package com.example.creditcomputator.config;

import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.creditcomputator.dto.request.AppUserRequest;
import com.example.creditcomputator.dto.request.CreditScoreRequest;
import com.example.creditcomputator.dto.response.AppUserResponse;
import com.example.creditcomputator.dto.response.CreditAppealResponse;
import com.example.creditcomputator.dto.response.CreditScoreResponse;
import com.example.creditcomputator.entity.AppUser;
import com.example.creditcomputator.entity.CreditAppeal;
import com.example.creditcomputator.entity.CreditScore;

@Configuration
public class ModelMapperConfig {
	
	private static final Converter<AppUser, AppUserResponse> 
		APP_USER_TO_APP_USER_RESPONSE_CONVERTER = 
			(context) -> {
		AppUser appUser = context.getSource();
		AppUserResponse appUserResponse = new AppUserResponse();
	
		appUserResponse.setNationalId(appUser.getNationalId());
		appUserResponse.setFullName(appUser.getFirstName() + " " + appUser.getLastName());
		appUserResponse.setMonthlyIncome(appUser.getMonthlyIncome());
		appUserResponse.setPhoneNumber(appUser.getPhoneNumber());
		appUserResponse.setCreatedAt(appUser.getCreatedAt());
		appUserResponse.setCreditScore(appUser.getCreditScore() == null ? null : appUser.getCreditScore().getScore());
		appUserResponse.setCreditScoreAssignedAt(appUser.getCreditScore() == null ? null : appUser.getCreditScore().getCreatedAt());
		appUserResponse.setCreditAppealIds(appUser.getCreditAppeals() == null ? null : appUser.getCreditAppeals().stream()
												.map(cA -> cA.getId())
												.collect(Collectors.toList()));
	
		System.err.println("APP_USER_TO_APP_USER_RESPONSE_CONVERTER: " + appUserResponse);
		return appUserResponse;
	};
	
	
	private static final Converter<AppUserRequest, AppUser>
		APP_USER_REQUEST_TO_APP_USER_CONVERTER =
			(context) -> {
		AppUserRequest appUserRequest = context.getSource();
		AppUser appUser = new AppUser();
		
		appUser.setNationalId(appUserRequest.getNationalId());
		appUser.setFirstName(appUserRequest.getFirstName());
		appUser.setLastName(appUserRequest.getLastName());
		appUser.setMonthlyIncome(appUserRequest.getMonthlyIncome());
		appUser.setPhoneNumber(appUserRequest.getPhoneNumber());
		
		System.err.println("APP_USER_REQUEST_TO_APP_USER_CONVERTER: " + appUser);
		return appUser;
	};
	
	
	
	private static final Converter<CreditScoreRequest, CreditScore> 
		CREDIT_SCORE_REQUEST_TO_CREDIT_SCORE_CONVERTER = 
			(context) -> {
		CreditScoreRequest creditScoreRequest = context.getSource();
		CreditScore creditScore = new CreditScore();

		creditScore.setScore(creditScoreRequest.getScore());
		
		System.err.println("CREDIT_SCORE_REQUEST_TO_CREDIT_SCORE_CONVERTER: " + creditScore);
		return creditScore;
	};
	

	
	
	private static final Converter<CreditScore, CreditScoreResponse> 
		CREDIT_SCORE_TO_CREDIT_SCORE_RESPONSE_CONVERTER = 
			(context) -> {
		CreditScore creditScore = context.getSource();

		CreditScoreResponse creditScoreResponse = CreditScoreResponse.builder()
						.id(creditScore.getId())
						.assignedCreditScore(creditScore.getScore())
						.createdAt(creditScore.getCreatedAt())
						.appUserFullName(creditScore.getAppUser().getFirstName() + " " + creditScore.getAppUser().getLastName())
						.appUserMonthlyIncome(creditScore.getAppUser().getMonthlyIncome())
						.appUserPhoneNumber(creditScore.getAppUser().getPhoneNumber())
						.build();
				
		System.err.println("CREDIT_SCORE_TO_CREDIT_SCORE_RESPONSE_CONVERTER: " + creditScoreResponse);
		return creditScoreResponse;
	};
	
	
	
	private static final Converter<CreditAppeal, CreditAppealResponse> 
		CREDIT_APPEAL_TO_CREDIT_APPEAL_RESPONSE_CONVERTER = 
				(context) -> {
			CreditAppeal creditAppeal = context.getSource();

			CreditAppealResponse creditAppealResponse = CreditAppealResponse.builder()
						.id(creditAppeal.getId())
						.assignedCreditLimit(creditAppeal.getCreditLimit())
						.creditApprovalStatus(creditAppeal.getCreditApprovalStatus())
						.createdAt(creditAppeal.getCreatedAt())
						.appUserNationalId(
								creditAppeal.getAppUser().getNationalId())
						.build();
						

			System.err.println("CREDIT_APPEAL_TO_CREDIT_APPEAL_RESPONSE_CONVERTER: " + creditAppealResponse);
			return creditAppealResponse;
	};
	

	
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(APP_USER_REQUEST_TO_APP_USER_CONVERTER, AppUserRequest.class, AppUser.class);
		modelMapper.addConverter(APP_USER_TO_APP_USER_RESPONSE_CONVERTER, AppUser.class, AppUserResponse.class);
		
		modelMapper.addConverter(CREDIT_SCORE_REQUEST_TO_CREDIT_SCORE_CONVERTER, CreditScoreRequest.class, CreditScore.class);
		modelMapper.addConverter(CREDIT_SCORE_TO_CREDIT_SCORE_RESPONSE_CONVERTER, CreditScore.class, CreditScoreResponse.class);
		
		modelMapper.addConverter(CREDIT_APPEAL_TO_CREDIT_APPEAL_RESPONSE_CONVERTER, CreditAppeal.class, CreditAppealResponse.class);
		return modelMapper;
	}
	

}
