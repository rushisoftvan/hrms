package com.ru.hrms_service.holiday.services.test;

import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.holiday.entities.HolidayEntity;
import com.ru.hrms_service.holiday.enums.HolidayStatusCodeEnum;
import com.ru.hrms_service.holiday.models.request.FetchHolidaysReq;
import com.ru.hrms_service.holiday.models.response.HolidayResponse;
import com.ru.hrms_service.holiday.repositories.HolidayRepo;
import com.ru.hrms_service.holiday.services.HolidayService;
import com.ru.hrms_service.holiday.services.mocks.HolidayTestMock;
import com.ru.hrms_service.holiday.specification.HolidaySpecification;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class HolidayServiceTest extends AbstractTest {
    @Mock
    HolidayRepo holidayRepo;


    @InjectMocks
    private HolidayService holidayService;

//    @Mock
//    HolidaySpecification holidaySpecification;



//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this); // Initialize mocks
//    }



   @BeforeEach
    void setUp1() {

        MockitoAnnotations.openMocks(this); ;
    }

    @Test
    void fetch_holiday_success() {
        FetchHolidaysReq fetchHolidaysReq = new FetchHolidaysReq(HolidayStatusCodeEnum.UPC, null);



        // Mock the behavior of the specification
        try (MockedStatic<HolidaySpecification> mockedStatic = mockStatic(HolidaySpecification.class)) {
            // Mock the static method call
            Specification<HolidayEntity> holidaysSpec = mock(Specification.class);
            mockedStatic.when(() -> HolidaySpecification.fetchHolidays(fetchHolidaysReq)).thenReturn(holidaysSpec);
            List<HolidayEntity> holidayEntities = new HolidayTestMock().setHolidayEntityList();
            // Mock the behavior of the repo

            when(holidayRepo.findAll(holidaysSpec)).thenReturn(holidayEntities);

            // Call the method under test
            ApiResponse response = holidayService.fetchHolidays(fetchHolidaysReq);

            // Verify the result
            assertNotNull(response);
            assertEquals("fetched successfully", response.getMessage());
            assertEquals(1, ((List<HolidayResponse>) response.getData()).size());

            // Verify that repo's findAll method was called
            verify(holidayRepo).findAll(holidaysSpec);

            // Verify that the static method was called
            mockedStatic.verify(() -> HolidaySpecification.fetchHolidays(fetchHolidaysReq));
        }

    }
}