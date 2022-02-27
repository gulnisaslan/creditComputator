package com.example.creditcomputator.service.business;

import com.example.creditcomputator.dto.request.AppUserRequest;
import com.example.creditcomputator.dto.response.AppUserResponse;
import com.example.creditcomputator.entity.AppUser;
import com.example.creditcomputator.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.verification.VerificationMode;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private  ModelMapper modelMapper;

    @InjectMocks
    private  AppUserServiceImpl appUserService;


    @Test
    void getAllAppUsers() {

       AppUser appUserResponse = new AppUser();
       AppUser appUserResponse1 = new AppUser();
       List<AppUser> appUsers = new ArrayList<>();
       appUsers.add(appUserResponse);
       appUsers.add(appUserResponse1);

       appUserResponse.setNationalId("12345678910");
       appUserResponse.setFirstName("Gulnisa");
       appUserResponse.setLastName("Aslan");
       appUserResponse.setMonthlyIncome(4000.0);
       appUserResponse.setPhoneNumber("5433121784");
       appUserResponse.setCreatedAt(LocalDateTime.now());


       appUserResponse1.setNationalId("12345688910");
       appUserResponse1.setFirstName("Gulnisa");
       appUserResponse1.setLastName("Aslan");
       appUserResponse1.setMonthlyIncome(4000.0);
       appUserResponse1.setPhoneNumber("5433121784");



       when(appUserRepository.findAll()).thenReturn(appUsers);

       List<AppUserResponse> collect = appUsers.stream()
               .map(appUser -> modelMapper.map(appUserResponse, AppUserResponse.class))
               .collect(Collectors.toList());

       assertEquals(appUsers.size(),collect.size());

    }

    @Test
    void getAppUsersByPagination() {
        AppUser appUser =new AppUser();
        List<AppUser>  appUsers=new ArrayList<>();
        appUsers.add(appUser);
        appUser.setNationalId("12345678910");
        appUser.setFirstName("Gulnisa");
        appUser.setLastName("Aslan");
        appUser.setMonthlyIncome(4000.0);
        appUser.setPhoneNumber("5433121784");
        appUser.setCreatedAt(LocalDateTime.now());

        int page =1;
        int size =1;
        Pageable pageable = PageRequest.of(page ,size);

        Page<AppUser> appUserPage = new PageImpl<>(Collections.singletonList(appUser));

        when(appUserRepository.findAll(pageable)).thenReturn(appUserPage);

        Page<AppUser> appUsers1 = appUserRepository.findAll(pageable);

        assertEquals(appUsers1.getNumberOfElements(),1);


    }

    @Test
    void getAppUserByNationalId() {
        String nationalId= "12345678910";
        AppUser appUser =new AppUser();
        List<AppUser>  appUsers=new ArrayList<>();
        appUsers.add(appUser);
        appUser.setNationalId("12345678910");
        appUser.setFirstName("Gulnisa");
        appUser.setLastName("Aslan");
        appUser.setMonthlyIncome(4000.0);
        appUser.setPhoneNumber("5433121784");
        appUser.setCreatedAt(LocalDateTime.now());
        appUser.getCreditScore();

        AppUserResponse map = modelMapper.map(appUser, AppUserResponse.class);
        when(appUserRepository.findById(nationalId)).thenThrow(new EntityNotFoundException());

        assertEquals(appUserRepository.getById(nationalId),map);


    }

    @Test
    void addAppUser() {

//        AppUserRequest appUser = new AppUserRequest();
//        appUser.setNationalId("12345678901");
//        appUser.setFirstName("Gülnisa");
//        appUser.setLastName("Aslan");
//        appUser.setMonthlyIncome(10000.00);
//        appUser.setPhoneNumber("5433125698");
//        AppUser map = modelMapper.map(appUser, AppUser.class);
//
//        when(appUserRepository.save(map)).thenThrow(new EntityExistsException());
//        when(appUserService.addAppUser(appUser)).thenThrow(new EntityExistsException());
//
//        verify(appUserRepository, times(1)).save(map);

    }


    @Test
    void updateAppUser() {
    }

    @Test
    void deleteAppUserByNationalIdThrowsEntityNotFoundException() {
//
//        given
//        String nationalId = "11111111110";
//
//        when
//        Mockito.when(appUserRepository.findById(nationalId))
//                .thenThrow(new EntityNotFoundException());
//        Mockito.when(appUserService.deleteAppUserByNationalId(nationalId))
//                .thenThrow(new EntityNotFoundException());
//
//        then
//        assertThrows


    }

    @Test
    void isAppUserPresentTrue() {

        //given
        String nationalId = "11111111111";

        //when
        Mockito.when(appUserRepository.existsById(nationalId)).thenReturn(true);

        //then
        assertEquals(true, appUserService.isAppUserPresent(nationalId));
    }

    @Test
    void isAppUserPresentFalse() {

        //given
        String nationalId = "11111111110";

        //when
        Mockito.when(appUserRepository.existsById(nationalId)).thenReturn(false);

        //then
        assertEquals(false, appUserService.isAppUserPresent(nationalId));

    }
}