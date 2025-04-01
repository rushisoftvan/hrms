package com.ru.hrms_service.common.repositories;

import com.ru.hrms_service.common.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findUserByIdAndDeleteFlagFalse(@Param("userId") Long userId);
}
