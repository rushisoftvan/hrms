package com.ru.hrms_service.holiday.repositories;

import com.ru.hrms_service.holiday.entities.BatchJobStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchJobStatusRepo extends JpaRepository<BatchJobStatusEntity , Long> {


}
