package com.ru.hrms_service.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Column
   private String firstName;




}
