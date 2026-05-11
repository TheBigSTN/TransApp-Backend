package com.app.trans.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDataDTO {
    private String name;
    private String description;
    private CompanyInfoDTO companyInfo;
    private List<CoreValueDTO> values;
    private List<CompanyServiceDTO> services;
    private List<TeamDepartmentDTO> teamSize;
}

