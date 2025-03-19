package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.common.projection.KeyValue;
import com.ru.hrms_service.leave.entities.LeaveTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeaveTypeRepo extends JpaRepository<LeaveTypeEntity, Long> {


    @Query(value = """
                  select id as  key, type as value from leave_type lt  where lt.delete_flag = false
            """,
            nativeQuery = true)
    List<KeyValue<Long, String>> fetchRequestType();

    @Query("""
            select l from  LeaveTypeEntity l
            where l.id = :leaveTypeID and l.deleteFlag = false
           """)
    Optional<LeaveTypeEntity> findLeaveTypeById(@Param("leaveTypeID") Long leaveTypeId);


}
