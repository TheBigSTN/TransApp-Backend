package com.app.trans.dtos;

import com.app.trans.models.enums.TipAlimentare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlimentareDTO {

    private long id;
    private long masinaId;
    private String masinaNumar;
    private LocalDate dataAlimentare;
    private Float litri;
    private Float pretUnitar;
    private TipAlimentare tip;
}
