package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.leave.entities.UserReporterEntity;
import com.ru.hrms_service.leave.entities.UserReportingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserReportingDetailRepo extends JpaRepository<UserReportingDetailEntity, Long> {
        @Query("""
              select u from UserReportingDetailEntity u
              join fetch  u.reportingManager where u.user.id = :userId and u.deleteFlag = false
              """)
       Optional<UserReportingDetailEntity> findUserReportingDetailByUserId(@Param("userId") Long userId);

}
