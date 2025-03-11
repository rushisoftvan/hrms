package com.ru.hrms_service.holiday.repositories;

import com.ru.hrms_service.holiday.entities.BatchJobStatusEntity;
import com.ru.hrms_service.holiday.enums.BatchJobStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BatchJobStatusRepo extends JpaRepository<BatchJobStatusEntity , Long> {


      Optional<BatchJobStatusEntity> findByIdAndDeleteFlagFalse(Long batchId);

      @Query(value = "SELECT iht.remarks FROM import_holiday_temp iht " +
              "WHERE iht.status = :status AND iht.batch_job_status_id = :batchId",
              nativeQuery = true)
      List<String> findRemarksByStatusAndBatchIdNative(
              @Param("status") String status,
              @Param("batchId") Long batchId
      );

            @Modifying
            @Transactional
            @Query("UPDATE BatchJobStatusEntity b SET b.status = :status WHERE b.id = :batchJobId")
            void updateBatchStatusAsCompleted(@Param("batchJobId") Long batchJobId, @Param("status") BatchJobStatusEnum status);
}
