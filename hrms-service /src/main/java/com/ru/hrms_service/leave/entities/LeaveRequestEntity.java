package com.ru.hrms_service.leave.entities;

import com.ru.hrms_service.common.entities.LongIdEntity;
import com.ru.hrms_service.common.entities.UserEntity;
import com.ru.hrms_service.leave.enums.LeaveStatusEnum;
import com.ru.hrms_service.leave.enums.LeaveTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name="leave_request")
public class LeaveRequestEntity extends LongIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false )
     private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id",nullable = false)
    private UserEntity manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveTypeEntity  leaveTypeEntity;

    @Column(name = "start_date", nullable = false )
    private Instant startDate;


    @Column(name = "end_date", nullable = false )
    private Instant endDate;


    @Enumerated(EnumType.STRING)
    @Column(name="leave_status",nullable = false)
    private LeaveStatusEnum leaveStatusEnum;

    @Column(name="leave_reason")
    private String leaveReason;


}
