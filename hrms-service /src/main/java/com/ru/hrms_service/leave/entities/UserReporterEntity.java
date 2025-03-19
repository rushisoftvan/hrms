package com.ru.hrms_service.leave.entities;

import com.ru.hrms_service.common.entities.LongIdEntity;
import com.ru.hrms_service.common.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="employee_manager_detail")
@AllArgsConstructor
@NoArgsConstructor
public class UserReporterEntity extends LongIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
   private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private UserEntity reportingManager;
}
