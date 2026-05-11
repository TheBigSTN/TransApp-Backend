package com.app.trans.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sofer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "data_emitere_permis")
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
    @ToString.Exclude()
    private List<Cursa> curse;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @ToString.Exclude()
    private Company company;
}
