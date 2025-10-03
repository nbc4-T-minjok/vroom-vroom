package com.sparta.vroomvroom.domain.user.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "black_lists")
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

    @CreatedBy
    @Column(name = "created_by",updatable = false, nullable = false, length = 20)
    private String createdBy;
}
