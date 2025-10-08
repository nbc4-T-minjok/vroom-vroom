package com.sparta.vroomvroom.domain.company.model.entity;

import com.sparta.vroomvroom.domain.company.model.dto.CompanyCategoryRequestDto;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "company_categories")
@Getter
@NoArgsConstructor
public class CompanyCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "company_category_id")
    private UUID companyCategoryId;

    @Column(name = "company_category_name", nullable = false, length = 20)
    private String companyCategoryName;

    public CompanyCategory(CompanyCategoryRequestDto requestDto) {
        this.companyCategoryName = requestDto.getCompanyCategoryName();
    }
}
