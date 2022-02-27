package com.example.creditcomputator.service.business;

import com.example.creditcomputator.dto.response.CreditScoreResponse;
import com.example.creditcomputator.entity.CreditScore;
import com.example.creditcomputator.repository.CreditScoreRepository;
import com.example.creditcomputator.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreditScoreServiceImplTest {

    @Mock
    private CreditScoreRepository creditScoreRepository;

    @Mock
    private AppUserService appUserService;

    @Mock
    private ModelMapper modelMapper;


    @InjectMocks
    private CreditScoreServiceImpl creditScoreServiceImpl;


    @Test
    void getAllCreditScores() {


        CreditScoreResponse creditScoreResponse1 = CreditScoreResponse.builder()
                .id("12345678910")
                .assignedCreditScore(800.00)
                .appUserFullName("Gülnisa Aslan")
                .appUserMonthlyIncome(6000.00)
                .appUserPhoneNumber("543335454454")
                .createdAt(LocalDateTime.now())
                .build();
        CreditScoreResponse creditScoreResponse2 = CreditScoreResponse.builder()
                .id("12345678910")
                .assignedCreditScore(800.00)
                .appUserFullName("Gülnisa Aslan")
                .appUserMonthlyIncome(6000.00)
                .appUserPhoneNumber("543335454454")
                .createdAt(LocalDateTime.now())
                .build();
        List<CreditScoreResponse> creditScoreResponseList= new ArrayList<>();
        creditScoreResponseList.add(creditScoreResponse1);
        creditScoreResponseList.add(creditScoreResponse2);



        List<CreditScore> creditScoreList = creditScoreResponseList.stream()
                .map(creditScoreResponse -> modelMapper.map(creditScoreResponse, CreditScore.class))
                .collect(Collectors.toList());

        Mockito.when(creditScoreRepository.findAll()).thenReturn(creditScoreList);

        assertEquals(creditScoreResponseList.size(),creditScoreList.size());


    }

    @Test
    void getCreditScoresByPagination() {
        List<CreditScoreResponse> creditScoreResponses = new ArrayList<>();

        CreditScoreResponse creditScoreResponse = CreditScoreResponse.builder()
                .id("12345678910")
                .assignedCreditScore(800.00)
                .appUserFullName("Gülnisa Aslan")
                .appUserMonthlyIncome(6000.00)
                .appUserPhoneNumber("543335454454")
                .createdAt(LocalDateTime.now())
                .build();
        CreditScoreResponse creditScoreResponse1 = CreditScoreResponse.builder()
                .id("12345678910")
                .assignedCreditScore(800.00)
                .appUserFullName("Gülnisa Aslan")
                .appUserMonthlyIncome(6000.00)
                .appUserPhoneNumber("543335454454")
                .createdAt(LocalDateTime.now())
                .build();
        creditScoreResponses.add(creditScoreResponse);
        creditScoreResponses.add(creditScoreResponse1);



        int page = 1;
        int size= 1;
        Pageable pageable = PageRequest.of(page,size);
        CreditScore map = modelMapper.map(creditScoreResponse, CreditScore.class);
        Page<CreditScore> scoreResponses = new PageImpl<>(Collections.singletonList(map));

        when(creditScoreRepository.findAll(pageable)).thenReturn(scoreResponses);

        assertEquals(scoreResponses.getNumberOfElements(),1);

    }

    @Test
    void getCreditScoreByAppUserNationalId() {







    }

    @Test
    void getCreditScoreById() {

    }

    @Test
    void assignCreditScore() {

    }

    @Test
    void updateCreditScoreByAppUserNationalId() {

    }

    @Test
    void deleteCreditScoreById_SuccessfullyDeleted() {

        //given
        String id = "56fgfdgd56906fdgdkgofdg";
        CreditScoreResponse expectedCreditScoreResponse =
                            CreditScoreResponse.builder()
                                                .id(id)
                                                .assignedCreditScore(800.0)
                                                .appUserFullName("İhsan Doğramacı")
                                                .appUserMonthlyIncome(5000.0)
                                                .appUserPhoneNumber("095680454353")
                                                .createdAt(LocalDateTime.parse("2022-02-26T19:41:42.955"))
                                                .build();

        CreditScore expectedCreditScore =
                CreditScore.builder()
                        .id(id)
                        .score(800.0)
                        .createdAt(LocalDateTime.parse("2022-02-26T19:41:42.955"))
                        .build();

        //when
        Optional<CreditScore> expectedOptionalCreditScore = Optional.of(expectedCreditScore);
        Mockito.when(creditScoreRepository.findById(id))
                                        .thenReturn(expectedOptionalCreditScore);
        //Mockito.when(creditScoreRepository.deleteById(expectedCreditScore.getId())).doNothing();
        Mockito.when(modelMapper.map(expectedCreditScore, CreditScoreResponse.class))
                                        .thenReturn(expectedCreditScoreResponse);

        //then
        assertEquals(expectedCreditScoreResponse, creditScoreServiceImpl.deleteCreditScoreById(id));

    }


    @Test
    void deleteCreditScoreById_EntityNotFound() {

        //given
        String id = "56fgfdgd56906fdgdkgofdg";

        // stub - when step
        Mockito.when(creditScoreRepository.findById(id)).thenReturn(Optional.empty());

        // then step
        assertThrows(EntityNotFoundException.class,
                () -> {
                    CreditScoreResponse creditScoreResponse = creditScoreServiceImpl.deleteCreditScoreById(id);
                }
        );

    }

    @Test
    void isCreditScorePresent_Success() {

        //given
        String id = "gfhty5ber5654wervw";

        // stub - when step
        Mockito.when(creditScoreRepository.existsById(id))
                .thenReturn(true);

        //then
        assertEquals(true, creditScoreServiceImpl.isCreditScorePresent(id));
    }

    @Test
    void isCreditScorePresent_Failure() {

        //given
        String id = "gfhty5ber5654wervw";

        // stub - when step
        Mockito.when(creditScoreRepository.existsById(id))
                .thenReturn(false);

        //then
        assertEquals(false, creditScoreServiceImpl.isCreditScorePresent(id));

    }

    @Test
    void isAppUserPresent_Successful() {

        //given
        String appUserNationalId = "12345678910";

        //when
        Mockito.when(appUserService.isAppUserPresent(appUserNationalId))
                .thenReturn(true);

        //then
        assertEquals(true, creditScoreServiceImpl.isAppUserPresent(appUserNationalId));

    }

    @Test
    void isAppUserPresent_Failure() {

        //given
        String appUserNationalId = "12345678910";

        //when
        Mockito.when(appUserService.isAppUserPresent(appUserNationalId))
                .thenReturn(false);

        //then
        assertEquals(false, creditScoreServiceImpl.isAppUserPresent(appUserNationalId));

    }


}