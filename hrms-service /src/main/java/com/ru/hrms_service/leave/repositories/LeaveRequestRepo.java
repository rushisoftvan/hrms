package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.leave.entities.LeaveRequestEntity;
import com.ru.hrms_service.leave.enums.LeaveStatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequestEntity, Long> {


    @Query(value = """
        SELECT COUNT(1) FROM  leave_request as l
        WHERE user_id = :userId 
        AND  (
            (start_date <= :endDate AND end_date >= :startDate) 
        )
    """, nativeQuery = true)
    int countOverlappingLeaves(@Param("userId") Long userId,
                               @Param("startDate") Instant startDate,
                               @Param("endDate") Instant endDate);


    @Query("""
          select l from  LeaveRequestEntity l 
            where l.id = :leaveRequestId and l.deleteFlag = false         
                    
          """)
    Optional<LeaveRequestEntity> findLLeaveRequest(@Param("leaveRequestId") Long leaveRequestId);

    @Query(value = """
            SELECT lr FROM LeaveRequestEntity lr \
            JOIN  lr.user u \
            JOIN  lr.leaveTypeEntity lte \
            WHERE 1=1 \
            AND (:leaveTypeId IS NULL OR lte.id = :leaveTypeId) \
            AND (:startDate IS NULL OR lr.startDate >= :startDate) \
            AND (:endDate IS NULL OR lr.endDate <= :endDate) \
            AND (:leaveStatus IS NULL OR lr.leaveStatusEnum = :leaveStatus)""")
    Page<LeaveRequestEntity> fetchLeaves(
            @Param("leaveTypeId") Long leaveTypeId,
            @Param("leaveStatus") LeaveStatusEnum leaveStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);

    @Query(value = """
            SELECT lr FROM LeaveRequestEntity lr \
            JOIN  lr.user u \
            JOIN  lr.leaveTypeEntity lte \
            WHERE 1=1 \
            AND (:leaveTypeId IS NULL OR lte.id = :leaveTypeId) \
            AND (:startDate IS NULL OR lr.startDate >= :startDate) \
            AND (:endDate IS NULL OR lr.endDate <= :endDate) \
            AND (:leaveStatus IS NULL OR lr.leaveStatusEnum = :leaveStatus)""")
    List<LeaveRequestEntity> fetchLeavesList(
            @Param("leaveTypeId") Long leaveTypeId,
            @Param("leaveStatus") LeaveStatusEnum leaveStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);
}
