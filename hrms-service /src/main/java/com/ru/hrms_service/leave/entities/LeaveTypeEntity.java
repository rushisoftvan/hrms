package com.ru.hrms_service.leave.entities;

import com.ru.hrms_service.common.entities.BaseEntity;
import com.ru.hrms_service.common.entities.LongIdEntity;
import com.ru.hrms_service.leave.enums.LeaveTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="leave_type")
public class LeaveTypeEntity extends LongIdEntity {

     @Enumerated(EnumType.STRING)
     @Column(name="type",nullable = false,unique = true)
     private LeaveTypeEnum type;
}
