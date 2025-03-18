package com.ru.hrms_service.common.entities;

import jakarta.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private RoleEntity roleEntity;

    public UserEntity(Long id) {
        this.id = id;
    }
}

