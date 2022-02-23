package com.example.creditcomputator;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = CreditComputatorApplication.class)
@AutoConfigureMockMvc
class CreditComputatorApplicationTests {
	
//	@Autowired private MockMvc mvc;
//	@MockBean
//	private CreditAppealService creditAppealService;

	@DisplayName("Credit approval status would be false and assigned credit limit would be null "
			+ "if made by app users with less than 500 credit score are")
	void assignNullLimitAndFalseApprovalStatusForLessThan500CreditScoreAppUsers() throws Throwable {
		
		
	}
	

}
