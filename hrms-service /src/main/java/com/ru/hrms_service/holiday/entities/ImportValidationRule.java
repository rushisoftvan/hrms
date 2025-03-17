package com.ru.hrms_service.holiday.entities;
import com.ru.hrms_service.common.entities.LongIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="import_validation_rule")
public class ImportValidationRule extends LongIdEntity {

    @Column(name="entity_name",nullable = false)
    private String entityName;

    @Column(name="column_name",nullable = false)
    private String columnName;

    @Column(name="regex_pattern")
    private String regexPattern;

    @Column(name="custom_error_message")
    private String custom_error_message;


}
