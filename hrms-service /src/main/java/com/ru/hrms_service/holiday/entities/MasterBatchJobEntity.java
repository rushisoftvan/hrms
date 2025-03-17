package com.ru.hrms_service.holiday.entities;

import com.ru.hrms_service.common.entities.BaseEntity;
import com.ru.hrms_service.common.entities.LongIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="master_batch_job")
public class MasterBatchJobEntity extends LongIdEntity {

    @Column(name="job_code",nullable = false)
    private String jobCode;

    @Column(name="description",nullable = false)
    private String description;

}
