package com.sparta.vroomvroom.domain.company.model.entity;

import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "company_categories")
@Getter
@Setter
@NoArgsConstructor
public class CompanyCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "company_category_id")
    private UUID companyCategoryId;

    @Column(name = "company_category_name", nullable = false, length = 20)
    private String companyCategoryName;
}
