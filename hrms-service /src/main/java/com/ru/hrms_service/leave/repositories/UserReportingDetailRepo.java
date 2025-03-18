package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.leave.entities.UserReporterEntity;
import com.ru.hrms_service.leave.entities.UserReportingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportingDetailRepo extends JpaRepository<UserReportingDetailEntity, Long> {
}
