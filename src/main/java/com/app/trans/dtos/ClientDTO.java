package com.app.trans.dtos;

import lombok.Data;

@Data
public class ClientDTO {
    private long id;
    private String nume;
    private String cui;
    private String adresaFacturare;
    private String adresaCorespondenta;
    private String cod;
    private String cont;
    private String banca;
    private String contact;
    private String email;
    private String telefon;

    public ClientDTO(long id, String nume, String cui, String adresaFacturare, String adresaCorespondenta, String cod, String cont, String banca, String contact, String email, String telefon) {
        this.id = id;
        this.nume = nume;
        this.cui = cui;
        this.adresaFacturare = adresaFacturare;
        this.adresaCorespondenta = adresaCorespondenta;
        this.cod = cod;
        this.cont = cont;
        this.banca = banca;
        this.contact = contact;
        this.email = email;
        this.telefon = telefon;
    }
}
