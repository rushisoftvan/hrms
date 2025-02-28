package com.ru.hrms_service.holiday.repositories;

import com.ru.hrms_service.holiday.entities.ImportHolidayTempEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImportHolidayTempRepo extends JpaRepository<ImportHolidayTempEntity, Long> {

    @Query("select t from ImportHolidayTempEntity t where t.batchJobStatusEntity.id = :batchId and t.deleteFlag=false")
    List<ImportHolidayTempEntity> findByBatchId(@Param("batchId") Long batchId);


    @Procedure(procedureName = "CompareAndInsertHolidays")
    void compareAndInsertHolidays(Long batchId);

    @Query(value = "SELECT iht.remarks FROM import_holiday_temp iht " +
            "WHERE iht.status = :status AND iht.batch_job_status_id = :batchId",
            nativeQuery = true)
    List<String> findRemarksByStatusAndBatchIdNative(
            @Param("status") String status,
            @Param("batchId") Long batchId
    );




}
