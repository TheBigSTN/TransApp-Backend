package com.app.trans.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cursa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cursa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "id_cursa")
    private long id;

    @Column(name = "km")
    private Integer km;

    @Column(name = "data_efectuare")
    private LocalDate dataEfectuare;
    
    //oras livrare/adresa livrare
    @Column(name = "livrare")
    private String livrare;
    
    @Column (name = "tarif")
    private Float tarif;
    
    @JsonIgnore
    @ManyToOne
    @ToString.Exclude()
    @JoinColumn(name = "id_masina")
    private Masina masina;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude()
    @JoinColumn(name = "id_sofer", nullable = false)
    private Sofer sofer;
    
    @JsonIgnore
    @ManyToOne
    @ToString.Exclude()
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @ToString.Exclude()
    @JoinColumn(name = "id_anexa")
    private Anexa anexa;

    @ManyToOne
    @ToString.Exclude()
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
