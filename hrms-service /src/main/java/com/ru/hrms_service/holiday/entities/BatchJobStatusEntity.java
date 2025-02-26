package com.ru.hrms_service.holiday.entities;


import com.ru.hrms_service.common.entities.BaseEntity;
import com.ru.hrms_service.common.entities.LongIdEntity;
import com.ru.hrms_service.holiday.enums.BatchJobStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="batch_job_status")
public class BatchJobStatusEntity extends LongIdEntity {

    @Column(name="job_code")
    private String jobCode;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private BatchJobStatusEnum status;


    @Column(name="remarks")
    private String remarks;

}
