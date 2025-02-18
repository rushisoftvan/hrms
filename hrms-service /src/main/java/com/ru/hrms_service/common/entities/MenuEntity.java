package com.ru.hrms_service.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="menu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuEntity  extends BaseEntity{

    @Column(name="name",nullable = false)
    private String  name;

}
