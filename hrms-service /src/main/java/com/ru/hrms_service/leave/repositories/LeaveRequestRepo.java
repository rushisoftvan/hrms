package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.leave.entities.LeaveRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequestEntity, Long> {
}
