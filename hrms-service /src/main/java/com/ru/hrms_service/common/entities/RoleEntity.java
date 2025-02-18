package com.ru.hrms_service.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="role")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity {

    @Column(name="role_name",nullable = false)
    private String roleName;
}
