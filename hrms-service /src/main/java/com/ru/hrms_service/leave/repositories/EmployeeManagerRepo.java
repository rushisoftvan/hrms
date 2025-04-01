package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.leave.entities.UserReporterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeManagerRepo extends JpaRepository<UserReporterEntity, Long> {


}
