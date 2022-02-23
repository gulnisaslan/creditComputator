package com.example.creditcomputator.service.business;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.creditcomputator.dto.response.AppUserResponse;
import com.example.creditcomputator.dto.response.CreditAppealResponse;
import com.example.creditcomputator.dto.response.CreditScoreResponse;
import com.example.creditcomputator.entity.AppUser;
import com.example.creditcomputator.entity.CreditAppeal;
import com.example.creditcomputator.notification.NotificationService;
import com.example.creditcomputator.repository.CreditAppealRepository;
import com.example.creditcomputator.service.AppUserService;
import com.example.creditcomputator.service.CreditAppealService;
import com.example.creditcomputator.service.CreditScoreService;
import com.example.creditcomputator.service.business.rules.BusinessRuleData;

@Service
public class CreditAppealServiceImpl implements CreditAppealService{
	
	private final CreditAppealRepository creditAppealRepository;
	private final ModelMapper modelMapper;
	private final CreditScoreService creditScoreService;
	private final NotificationService loggerService;
	private final AppUserService appUserService;
	private final BusinessRuleService businessRuleService;
	
	public CreditAppealServiceImpl(CreditAppealRepository creditAppealRepository, ModelMapper modelMapper,
			CreditScoreService creditScoreService, NotificationService loggerService, AppUserService appUserService,
			BusinessRuleService businessRuleService) {
		this.creditAppealRepository = creditAppealRepository;
		this.modelMapper = modelMapper;
		this.creditScoreService = creditScoreService;
		this.loggerService = loggerService;
		this.appUserService = appUserService;
		this.businessRuleService = businessRuleService;
	}


	@Override
	public List<CreditAppealResponse> getCreditAppealsByAppUserNationalId(String appUserNationalId) {
		List<CreditAppeal> creditAppeals = creditAppealRepository.findByAppUser_NationalId(appUserNationalId);
		return creditAppeals.stream()
				.map( creditAppeal -> modelMapper.map(creditAppeal, CreditAppealResponse.class) )
				.collect( Collectors.toList() );
	}

	
	@Override
	public CreditAppealResponse deleteCreditAppealById(String id) {
		CreditAppeal creditAppeal = creditAppealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		creditAppealRepository.deleteById(creditAppeal.getId());
		return modelMapper.map(creditAppeal, CreditAppealResponse.class);
	}

	@Override
	public Boolean isCreditAppealPresent(String id) {
		return creditAppealRepository.existsById(id);
	}
	
	
	
	@Override
	public CreditAppealResponse appealForCredit(String appUserNationalId) {
		
		AppUserResponse appUserResponse = appUserService.getAppUserByNationalId(appUserNationalId);
		CreditScoreResponse creditScoreResponse = creditScoreService.getCreditScoreByAppUserNationalId(appUserNationalId);
		
		BusinessRuleData businessRuleData = new BusinessRuleData(creditScoreResponse.getAssignedCreditScore(), appUserResponse.getMonthlyIncome());
		
		Optional<CreditAppeal> creditAppealOptional = businessRuleService.getCreditAppeal(businessRuleData);
	
		if( !creditAppealOptional.isPresent() ) {
			throw new EntityNotFoundException();
		}
		
		creditAppealOptional.get().setAppUser( modelMapper.map(appUserResponse, AppUser.class) );
		CreditAppeal creditAppeal = creditAppealRepository.save( creditAppealOptional.get() );
		
		
		AppUser appUser = modelMapper.map(appUserResponse, AppUser.class);
		
		loggerService.logCreditLimitAppeal(appUser, creditAppealOptional.get().getCreditLimit(), 
									creditAppealOptional.get().getCreditApprovalStatus(), 
									creditAppealOptional.get().getCreatedAt());
		
		return modelMapper.map(creditAppeal, CreditAppealResponse.class);
		
	}
	
	
	
}
