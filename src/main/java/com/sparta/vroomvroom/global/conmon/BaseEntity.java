package com.sparta.vroomvroom.global.conmon;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    //시큐리티 적용 되어있어야 동작
    @CreatedBy
    @Column(name = "created_by",updatable = false, nullable = false, length = 20)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "deleted_at", updatable = false)
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", updatable = false, length = 20)
    private String deletedBy;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public void softDelete(LocalDateTime deletedAt, String deletedBy) {
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = true;
    }

    public void update(String userName){
        this.updatedBy = userName;
    }

    public void create(String userName){
        this.createdBy = userName;
        this.updatedBy = userName;
    }

}
