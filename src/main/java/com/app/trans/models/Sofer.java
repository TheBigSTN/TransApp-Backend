package com.app.trans.models;

import com.app.trans.dtos.SoferDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="sofer")
@Setter
@Getter
@ToString  (exclude = {"curse"})
@EqualsAndHashCode
public class Sofer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "id_sofer")
    private long id;

    @Column(name = "nume_sofer")
    private String nume;

    @Column(name = "prenume_sofer")
    private String prenume;

    @Column(name = "data_nastere")
    private LocalDate dataNastere;

    @Column(name = "cnp")
    private String cnp;

    @Column(name = "serie_permis")
    private String seriePermis;

    @Column(name = "data_emitere_permis ")
    private LocalDate dataEmiterePermis;

    @Column(name = "data_expirare_permis")
    private LocalDate dataExpirarePermis;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "telefon_sofer")
    private String telefon;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "sofer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cursa> curse;



    public Sofer(long id, String nume, String prenume, LocalDate dataNastere, String cnp, String seriePermis, LocalDate dataEmiterePermis, LocalDate dataExpirare_permis, String address, String telefon, String email) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.dataNastere = dataNastere;
        this.cnp = cnp;
        this.seriePermis = seriePermis;
        this.dataEmiterePermis = dataEmiterePermis;
        this.dataExpirarePermis = dataExpirare_permis;
        this.adresa = address;
        this.telefon = telefon;
        this.email = email;
    }

    public Sofer(SoferDTO soferDTO) {
        this.id = soferDTO.getId();
        this.nume = soferDTO.getNume();
        this.prenume = soferDTO.getPrenume();
        this.dataNastere = soferDTO.getDataNastere();
        this.cnp = soferDTO.getCnp();
        this.seriePermis = soferDTO.getSeriePermis();
        this.dataEmiterePermis = soferDTO.getDataEmiterePermis();
        this.dataExpirarePermis = soferDTO.getDataExpirarePermis();
        this.adresa = soferDTO.getAdresa();
        this.telefon = soferDTO.getTelefon();
        this.email = soferDTO.getEmail();
    }
    
    public Sofer() {
    }
}
