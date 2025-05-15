package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.common.entities.UserEntity;
import com.ru.hrms_service.leave.entities.UserLeaveCountEntity;
import com.ru.hrms_service.leave.projection.LeaveCountDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserLeveCountRepo extends JpaRepository<UserLeaveCountEntity, Long > {

    @Query(value = """
            select ultc.available_count from user_leave_type_count  as ultc
            where ultc.user_id = :userId  and ultc.leave_type_id = :leaveTypeId and ultc.delete_flag=false
          """, nativeQuery = true)
   Integer findAvailableCountOfLeaveType(@Param("userId") Long userId , @Param("leaveTypeId") Long leaveTypeId );

    Long user(UserEntity user);


    @Query(value = """
            select lt.type, uc.available_count, uc.booked_count from user_leave_type_count uc
            join leave_type lt on lt.id = uc.leave_type_id where uc.user_id = :userId""", nativeQuery = true)
    List<LeaveCountDetailProjection> fetchUserLeaveCountDetail(@Param("userId") Long userId);
}
