package com.app.trans.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SoferDTO {

    private long id;
    private String nume;
    private String prenume;
    private LocalDate dataNastere;
    private String cnp;
    private String seriePermis;
    private LocalDate dataEmiterePermis;
    private LocalDate dataExpirarePermis;
    private String adresa;
    private String telefon;
    private String email;

    // Constructor with fields
    public SoferDTO(long id, String nume, String prenume, LocalDate dataNastere, String cnp, String seriePermis, LocalDate dataEmiterePermis, LocalDate dataExpirarePermis, String adresa, String telefon, String email) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.dataNastere = dataNastere;
        this.cnp = cnp;
        this.seriePermis = seriePermis;
        this.dataEmiterePermis = dataEmiterePermis;
        this.dataExpirarePermis = dataExpirarePermis;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
    }

    // Empty constructor
    public SoferDTO() {
    }
}
