package com.example.creditcomputator.service.business;

import com.example.creditcomputator.dto.response.AppUserResponse;
import com.example.creditcomputator.dto.response.CreditAppealResponse;
import com.example.creditcomputator.dto.response.CreditScoreResponse;
import com.example.creditcomputator.entity.CreditAppeal;
import com.example.creditcomputator.notification.NotificationService;
import com.example.creditcomputator.repository.CreditAppealRepository;
import com.example.creditcomputator.service.AppUserService;
import com.example.creditcomputator.service.CreditScoreService;
import com.example.creditcomputator.service.business.rules.BusinessRuleData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditAppealServiceImplTest {

    private CreditAppealServiceImpl creditAppealServiceImpl;

    private CreditAppealRepository creditAppealRepository;
    private ModelMapper modelMapper;
    private CreditScoreService creditScoreService;
    private NotificationService notificationService;
    private AppUserService appUserService;
    private BusinessRuleService businessRuleService;

    @BeforeEach
    void setUp() {
        creditAppealRepository = Mockito.mock(CreditAppealRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        creditScoreService = Mockito.mock(CreditScoreService.class);
        notificationService = Mockito.mock(NotificationService.class);
        appUserService = Mockito.mock(AppUserService.class);
        businessRuleService = Mockito.mock(BusinessRuleService.class);

        creditAppealServiceImpl = new CreditAppealServiceImpl(creditAppealRepository,
                modelMapper, creditScoreService,
                notificationService, appUserService,
                businessRuleService
        );

    }

    @Test
    void getCreditAppealsByAppUserNationalId() {
        String appUserNationalId = "12345678910";

        Mockito.when(creditAppealRepository.findByAppUser_NationalId(appUserNationalId))
                .thenReturn(new ArrayList<CreditAppeal>());

    }

    @Test
    void deleteCreditAppealById() {
        //Given
        String id = "dldrsjgöşlwkdk5";

        CreditAppealResponse expectedCreditAppealResponse = CreditAppealResponse.builder()
                .id(id)
                .appUserNationalId("12345678910")
                .assignedCreditLimit(10000.00)
                .creditApprovalStatus(true)
                .createdAt(LocalDateTime.now())
                .build();

        CreditAppeal exceptedCreditAppeal = CreditAppeal.builder()
                .id(id)
                .creditLimit(10000.00)
                .creditApprovalStatus(true)
                .createdAt(LocalDateTime.now())
                .build();


    }

    @Test
    void isCreditAppealPresent_Successful() {

        //given
        String id = "123456799";

        //when
        Mockito.when(creditAppealRepository.existsById(id)).thenReturn(true);

        //then
        assertEquals(true, creditAppealServiceImpl.isCreditAppealPresent(id));
    }

    @Test
    void isCreditAppealPresent_Failure() {

        //given
        String id = "123456799";

        //when
        Mockito.when(creditAppealRepository.existsById(id)).thenReturn(false);

        //then
        assertEquals(false, creditAppealServiceImpl.isCreditAppealPresent(id));
    }


    @Test
    public void whenBusinessRuleService_GetCreditAppealReturnsEmpty_ThrowEntityNotFoundException() throws Throwable {

        //given
        String appUserNationalId = "11111111110";

        AppUserResponse appUserResponse = AppUserResponse.builder()

                .monthlyIncome(8000.0)
                .nationalId(appUserNationalId)
                .build();

        CreditScoreResponse creditScoreResponse = CreditScoreResponse.builder()
                .assignedCreditScore(490.0)
                .build();

        BusinessRuleData businessRuleData = new BusinessRuleData(creditScoreResponse.getAssignedCreditScore(),
                appUserResponse.getMonthlyIncome());


        //when
        Mockito.when(appUserService.getAppUserByNationalId(appUserNationalId))
                .thenReturn(appUserResponse);

        Mockito.when(creditScoreService.getCreditScoreByAppUserNationalId(appUserNationalId))
                .thenReturn(creditScoreResponse);

        Mockito.when(businessRuleService.getCreditAppeal(businessRuleData))
                .thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class,
                () -> {
                    CreditAppealResponse creditAppealResponse = creditAppealServiceImpl.appealForCredit(appUserNationalId);
                }
        );

    }


}
