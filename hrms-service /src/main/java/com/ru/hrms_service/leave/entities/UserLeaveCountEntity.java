package com.ru.hrms_service.leave.entities;

import com.ru.hrms_service.common.entities.LongIdEntity;
import com.ru.hrms_service.common.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "employee_leave_type_count")
@AllArgsConstructor
@NoArgsConstructor
public class UserLeaveCountEntity extends LongIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveTypeEntity leaveTypeEntity;

    @Column(name="available_count",nullable = false)
    private double availableCount;

    @Column(name="booked_count",nullable = false)
    private double bookedCount;

}
