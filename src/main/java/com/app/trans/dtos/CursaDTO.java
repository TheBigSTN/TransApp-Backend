package com.app.trans.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursaDTO {

    private long id;
    private Integer km;
    private LocalDate dataEfectuare;
    private long idMasina;
    private long idSofer;
    private long idClient;
    private Long idAnexa;  // Nullable, as indicated in the entity
    private String masinaNumar;
    private String numeSofer;
    private String numeClient;
    private String livrare;
    private Float tarif;
}
