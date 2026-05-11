package com.app.trans.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfoDTO {
    private String name;
    private Integer foundedYear;
    private String employees;
    private String headquarters;
    private String mission;
    private String vision;
}
