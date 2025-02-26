package com.ru.hrms_service.holiday.repositories;

import com.ru.hrms_service.holiday.entities.MasterBatchJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterBachJobRepo extends JpaRepository<MasterBatchJobEntity,Long> {

    Optional<MasterBatchJobEntity> findByJobCodeAndDeleteFlagFalse(String jobCode);






}
