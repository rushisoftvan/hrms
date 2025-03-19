package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.common.entities.UserEntity;
import com.ru.hrms_service.leave.entities.UserLeaveCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLeveCountRepo extends JpaRepository<UserLeaveCountEntity, Long > {

    @Query(value = """
            select ultc.available_count from user_leave_type_count  as ultc
            where ultc.user_id = :userId  and ultc.leave_type_id = :leaveTypeId and ultc.delete_flag=false
          """, nativeQuery = true)
   int findAvailableCountOfLeaveType(@Param("userId") Long userId , @Param("leaveTypeId") Long leaveTypeId );

    Long user(UserEntity user);
}
