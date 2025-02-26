package com.ru.hrms_service.holiday.repositories;

import com.ru.hrms_service.holiday.entities.ValidationRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValidationRuleRepo extends JpaRepository<ValidationRuleEntity, Long> {

    List<ValidationRuleEntity> findByEntityNameAndDeleteFlagFalse(String entityName);
}
