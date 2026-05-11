package com.app.trans.dtos;

import java.time.LocalDate;

import com.app.trans.models.enums.TipLicenta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicentaDTO {
    private long id;
    private long masinaId;
    private String masinaNumar;
    private String serie;
    private TipLicenta tip;
    private LocalDate data_inceput;
    private LocalDate data_final;
    private Float pret;
}
