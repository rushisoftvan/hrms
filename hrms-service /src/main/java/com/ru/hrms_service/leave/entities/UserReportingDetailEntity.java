package com.ru.hrms_service.leave.entities;

import com.ru.hrms_service.common.entities.LongIdEntity;
import com.ru.hrms_service.common.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user_reporting_detail")
public class UserReportingDetailEntity  extends LongIdEntity  {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repoter_id", nullable = false)
    private UserEntity reportingManager;
}
