package com.ru.hrms_service.holiday.repositories;

import com.ru.hrms_service.holiday.entities.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HolidayRepo extends JpaRepository<HolidayEntity,Long> , JpaSpecificationExecutor<HolidayEntity> {



}
