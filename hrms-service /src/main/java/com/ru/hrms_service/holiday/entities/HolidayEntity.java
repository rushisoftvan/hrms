package com.ru.hrms_service.holiday.entities;

import com.ru.hrms_service.common.entities.BaseEntity;
import com.ru.hrms_service.common.entities.LongIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="holiday")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayEntity  extends LongIdEntity {

    @Column(name="name")
    private String name;

    @Column(name="holiday_date")
    private LocalDate holidayDate;

    @Column(name="is_optional")
    private boolean isOptional;

}
