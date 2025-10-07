package com.sparta.vroomvroom.domain.user.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "black_lists")
@EntityListeners(AuditingEntityListener.class)
public class BlackList {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "black_list_id")
    private UUID blackListId;

    @Column(name = "token",nullable = false)
    private String token;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    //Todo: ERD, 설계문서에서 CreatedBy 삭제 - 로직상 존재 불가능


    public BlackList(String token) {
        this.token = token;
    }
}
