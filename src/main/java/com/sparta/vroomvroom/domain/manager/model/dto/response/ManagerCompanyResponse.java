package com.sparta.vroomvroom.domain.manager.model.dto.response;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ManagerCompanyResponse {

    private UUID companyId;
    private String companyName;

    public ManagerCompanyResponse(Company company) {
        this.companyId = company.getCompanyId();
        this.companyName = company.getCompanyName();
    }
}
