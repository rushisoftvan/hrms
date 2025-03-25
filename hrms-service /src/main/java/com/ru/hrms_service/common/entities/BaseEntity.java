package com.ru.hrms_service.common.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public  class BaseEntity{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private Boolean deleteFlag;

    private Boolean active;

    private Instant createdOn;

    private Instant updatedOn;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by")
    private UserEntity createdBy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="updated_by")
    private UserEntity updatedBy;



    @PrePersist
    public void prePersist(){
        this.deleteFlag = false;
        this.active=true;
        this.createdOn=Instant.now();
        this.updatedOn=createdOn;

    }
}
