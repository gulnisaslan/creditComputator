package com.example.creditcomputator.service.business;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.creditcomputator.dto.request.CreditScoreRequest;
import com.example.creditcomputator.dto.response.CreditScoreResponse;
import com.example.creditcomputator.entity.AppUser;
import com.example.creditcomputator.entity.CreditScore;
import com.example.creditcomputator.repository.CreditScoreRepository;
import com.example.creditcomputator.service.AppUserService;
import com.example.creditcomputator.service.CreditScoreService;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {

	private final CreditScoreRepository creditScoreRepository;
	private final ModelMapper modelMapper;
	private final AppUserService appUserService;

	public CreditScoreServiceImpl(CreditScoreRepository creditScoreRepository, ModelMapper modelMapper,
			AppUserService appUserService) {
		this.creditScoreRepository = creditScoreRepository;
		this.modelMapper = modelMapper;
		this.appUserService = appUserService;
	}

	@Override
	public List<CreditScoreResponse> getAllCreditScores() {
		List<CreditScore> creditScores = creditScoreRepository.findAll();
		return creditScores.stream()
					.map( creditScore -> modelMapper.map(creditScore, CreditScoreResponse.class) )
					.collect(Collectors.toList());
	}

	@Override
	public List<CreditScoreResponse> getCreditScoresByPagination(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return creditScoreRepository.findAll(pageRequest).stream()
				.map( creditScore -> modelMapper.map(creditScore, CreditScoreResponse.class) )
				.collect(Collectors.toList());
	}

	@Override
	public CreditScoreResponse getCreditScoreByAppUserNationalId(String appUserNationalId) {
		CreditScore creditScore = creditScoreRepository.findByAppUser_NationalId(appUserNationalId).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(creditScore, CreditScoreResponse.class);
	}
	
	@Override
	public CreditScoreResponse getCreditScoreById(String id) {
		CreditScore creditScore = creditScoreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(creditScore, CreditScoreResponse.class);
	}

	@Override
	public CreditScoreResponse assignCreditScore(CreditScoreRequest creditScoreRequest) {
	
		if(!isAppUserPresent(creditScoreRequest.getAppUserNationalId())) {
			throw new EntityNotFoundException();
		}
		
		AppUser appUser = new AppUser();
		appUser.setNationalId(creditScoreRequest.getAppUserNationalId());
		
		
		CreditScore creditScoreToSave = modelMapper.map(creditScoreRequest, CreditScore.class);
		creditScoreToSave.setAppUser(appUser);
		
		if(isCreditScorePresent(creditScoreToSave.getId())) {
			throw new EntityExistsException();
		} //even though the possibility of the same UUID is extremely low, 
		//i still want to perform some existence check
		
		CreditScore savedCreditScore = creditScoreRepository.save(creditScoreToSave);
		
		return modelMapper.map(savedCreditScore, CreditScoreResponse.class);
		
	}

	@Override
	public CreditScoreResponse updateCreditScoreByAppUserNationalId(CreditScoreRequest creditScoreRequest) {
		
		if(!isAppUserPresent(creditScoreRequest.getAppUserNationalId())) {
			throw new EntityNotFoundException();
		}
		
		CreditScore creditScoreToUpdate = creditScoreRepository
								.findByAppUser_NationalId(creditScoreRequest.getAppUserNationalId())
								.orElseThrow(() -> new EntityNotFoundException());
		
		System.err.println(creditScoreToUpdate.getAppUser().getNationalId().equals(creditScoreRequest.getAppUserNationalId()));
		
		creditScoreToUpdate.setScore(creditScoreRequest.getScore());
		
		CreditScore updatedCreditScore = creditScoreRepository.save(creditScoreToUpdate);

		return modelMapper.map(updatedCreditScore, CreditScoreResponse.class);
	}
	

	@Override
	public CreditScoreResponse deleteCreditScoreById(String id) {
		CreditScore creditScore = creditScoreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		creditScoreRepository.deleteById(creditScore.getId());
		return modelMapper.map(creditScore, CreditScoreResponse.class);
	}

	@Override
	public Boolean isCreditScorePresent(String id) {
		return creditScoreRepository.existsById(id);
	}

	
	public Boolean isAppUserPresent(String appUserNationalId) {
		return appUserService.isAppUserPresent(appUserNationalId);
	}
	

	

}
