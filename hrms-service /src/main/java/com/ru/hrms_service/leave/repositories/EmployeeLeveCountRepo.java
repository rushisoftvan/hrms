package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.leave.entities.UserLeaveCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeLeveCountRepo  extends JpaRepository<UserLeaveCountEntity, Long > {

}
