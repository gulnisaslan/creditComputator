package com.example.creditcomputator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.creditcomputator.dto.request.AppUserRequest;
import com.example.creditcomputator.dto.response.AppUserResponse;
import com.example.creditcomputator.service.AppUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestScope
@RequestMapping("/api/appusers")
@CrossOrigin
@Api(tags = "App Users")
public class AppUserController {
	
	private final AppUserService appUserService;

	public AppUserController(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	@GetMapping("/getAllAppUsers")
	@ApiOperation(value = "This method is used to get all the app users")
	List<AppUserResponse> getAllAppUsers(){
		return appUserService.getAllAppUsers();
	}
	
	@GetMapping("/getAppUsersByPagination")
	List<AppUserResponse> getAppUsersByPagination(@RequestParam int page, @RequestParam int size){
		return appUserService.getAppUsersByPagination(page, size);
	}
	
	@GetMapping("/getAppUserByNationalId")
	AppUserResponse getAppUserByNationalId(@RequestParam String nationalId) {
		return appUserService.getAppUserByNationalId(nationalId);
	}
	
	@PostMapping("/addAppUser")
	AppUserResponse addAppUser(@RequestBody AppUserRequest appUserRequest) {
		return appUserService.addAppUser(appUserRequest);
	}
	
	@PutMapping("/updateAppUser")
	AppUserResponse updateAppUser(AppUserRequest appUserRequest) {
		return appUserService.updateAppUser(appUserRequest);
	}
	
	
	@DeleteMapping("/deleteAppUserByNationalId")
	AppUserResponse deleteAppUserByNationalId(String nationalId) {
		return appUserService.deleteAppUserByNationalId(nationalId);
	}
	

}
