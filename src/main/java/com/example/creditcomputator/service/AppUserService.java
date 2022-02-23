package com.example.creditcomputator.service;

import java.util.List;

import com.example.creditcomputator.dto.request.AppUserRequest;
import com.example.creditcomputator.dto.response.AppUserResponse;

public interface AppUserService {
	
	List<AppUserResponse> getAllAppUsers();
	
	List<AppUserResponse> getAppUsersByPagination(int page, int size);
	
	AppUserResponse getAppUserByNationalId(String nationalId);
	
	Boolean isAppUserPresent(String nationalId);
	
	AppUserResponse addAppUser(AppUserRequest appUserRequest);
	
	AppUserResponse updateAppUser(AppUserRequest appUserRequest);
	
	AppUserResponse deleteAppUserByNationalId(String nationalId);
	

}

