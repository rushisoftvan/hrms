package com.ru.hrms_service.leave.repositories;

import com.ru.hrms_service.common.entities.UserRoleMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleMappingRepo extends JpaRepository<UserRoleMappingEntity, Long> {


}
