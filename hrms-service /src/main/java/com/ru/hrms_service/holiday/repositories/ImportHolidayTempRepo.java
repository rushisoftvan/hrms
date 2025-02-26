package com.ru.hrms_service.holiday.repositories;

import com.ru.hrms_service.holiday.entities.ImportHolidayTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImportHolidayTempRepo extends JpaRepository<ImportHolidayTemp,Long> {

    @Query("select t from ImportHolidayTemp t where t.batchJobStatusEntity.id = :batchId and t.deleteFlag=false" )
    List<ImportHolidayTemp> findByBatchId(@Param("batchId") Long batchId);

}
