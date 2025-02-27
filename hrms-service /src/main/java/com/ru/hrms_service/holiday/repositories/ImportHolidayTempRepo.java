package com.ru.hrms_service.holiday.repositories;

import com.ru.hrms_service.holiday.entities.ImportHolidayTempEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImportHolidayTempRepo extends JpaRepository<ImportHolidayTempEntity,Long> {

    @Query("select t from ImportHolidayTempEntity t where t.batchJobStatusEntity.id = :batchId and t.deleteFlag=false" )
    List<ImportHolidayTempEntity> findByBatchId(@Param("batchId") Long batchId);

}
