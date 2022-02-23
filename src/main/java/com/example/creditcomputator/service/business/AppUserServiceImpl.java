package com.example.creditcomputator.service.business;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.creditcomputator.dto.request.AppUserRequest;
import com.example.creditcomputator.dto.response.AppUserResponse;
import com.example.creditcomputator.entity.AppUser;
import com.example.creditcomputator.repository.AppUserRepository;
import com.example.creditcomputator.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {

	private final AppUserRepository appUserRepository;
	private final ModelMapper modelMapper;

	public AppUserServiceImpl(AppUserRepository appUserRepository, ModelMapper modelMapper) {
		this.appUserRepository = appUserRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public List<AppUserResponse> getAllAppUsers() {
		List<AppUser> appUsers = appUserRepository.findAll();
		return appUsers.stream()
					.map( appUser -> modelMapper.map(appUser, AppUserResponse.class) )
					.collect(Collectors.toList());
	}
	
	@Override
	public List<AppUserResponse> getAppUsersByPagination(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return appUserRepository.findAll(pageRequest).stream()
				.map( appUser -> modelMapper.map(appUser, AppUserResponse.class) )
				.collect(Collectors.toList());
	}

	@Override
	public AppUserResponse getAppUserByNationalId(String nationalId) {
		AppUser appUser = appUserRepository.findById(nationalId).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(appUser, AppUserResponse.class);
	}
	

	@Override
	public AppUserResponse addAppUser(AppUserRequest appUserRequest) {
		
		if(isAppUserPresent(appUserRequest.getNationalId())) {
			throw new EntityExistsException();
		}
		
		AppUser appUser = appUserRepository.save(modelMapper.map(appUserRequest, AppUser.class));
		
		return modelMapper.map(appUser, AppUserResponse.class);
	}
	

	@Override
	public AppUserResponse updateAppUser(AppUserRequest appUserRequest) {
	
		AppUser appUserToUpdate = appUserRepository.findById(appUserRequest.getNationalId())
							.orElseThrow(() -> new EntityNotFoundException());
		
		appUserToUpdate.setFirstName(appUserRequest.getFirstName() == null ? appUserToUpdate.getFirstName() : appUserRequest.getFirstName());
		appUserToUpdate.setLastName(appUserRequest.getLastName() == null ? appUserToUpdate.getLastName() : appUserRequest.getLastName());
		appUserToUpdate.setMonthlyIncome(appUserRequest.getMonthlyIncome() == null ? appUserToUpdate.getMonthlyIncome() : appUserRequest.getMonthlyIncome());
		appUserToUpdate.setPhoneNumber(appUserRequest.getPhoneNumber() == null ? appUserToUpdate.getPhoneNumber() : appUserRequest.getPhoneNumber());
		
		AppUser updatedAppUser = appUserRepository.save(appUserToUpdate);
		return modelMapper.map(updatedAppUser, AppUserResponse.class);
	}

	@Override
	public AppUserResponse deleteAppUserByNationalId(String nationalId) {
		AppUser appUser = appUserRepository.findById(nationalId).orElseThrow(() -> new EntityNotFoundException());
		appUserRepository.delete(appUser);
		return modelMapper.map(appUser, AppUserResponse.class);
	}
	

	@Override
	public Boolean isAppUserPresent(String nationalId) {
		return appUserRepository.existsById(nationalId);
	}
	
	
}
