package com.ru.hrms_service.holiday.repositories;

import com.ru.hrms_service.holiday.entities.BatchJobStatusEntity;
import com.ru.hrms_service.holiday.entities.MasterBatchJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BatchJobStatusRepo extends JpaRepository<BatchJobStatusEntity , Long> {


      Optional<BatchJobStatusEntity> findByIdAndDeleteFlagFalse(Long batchId);


}
