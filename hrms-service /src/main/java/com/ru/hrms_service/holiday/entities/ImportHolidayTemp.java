package com.ru.hrms_service.holiday.entities;

import com.ru.hrms_service.common.entities.BaseEntity;
import com.ru.hrms_service.common.entities.LongIdEntity;
import com.ru.hrms_service.holiday.enums.ImportHolidayStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="import_holiday_temp")
public class ImportHolidayTemp extends LongIdEntity {

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="batch_job_status_id")
    private BatchJobStatusEntity batchJobStatusEntity;

    @Column(name="holiday_name")
    private String holidayName;

    @Column(name="holiday_date")
    private LocalDate holidayDate;

    @Column(name="is_optional")
    private boolean isOptionl;


    @Column(name="row_no")
    private  Integer rowNumber;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ImportHolidayStatusEnum status;

    @Column(name="remarks")
    private String remarks;


}
