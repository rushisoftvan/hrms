package com.ru.hrms_service.holiday.entities;

import com.ru.hrms_service.common.entities.LongIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="import_validation_rule")
public class ValidationRuleEntity  extends LongIdEntity {

    @Column(name="entity_name")
    private String entityName;

    @Column(name="column_name")
    private String columnName;

    @Column(name="regex_pattern")
    private String  regexPattern;

    @Column(name="is_required")
    private  boolean  isRequired;

    @Column(name="custom_error_message")
    private String  customErrorMsg;

}
