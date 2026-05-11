package com.app.trans.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "client")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "id_client")
    private long id;

    @Column(name = "nume_client")
    private String nume;

    @Column(name = "cui")
    private String cui;

    @Column(name = "adresa_facturare")
    private String adresaFacturare;

    @Column(name = "adresa_corespondenta")
    private String adresaCorespondenta;

    @Column(name = "cod_registrul_comertului")
    private String cod;

    @Column(name = "cont_bancar")
    private String cont;

    @Column(name = "nume_banca")
    private String banca;

    @Column(name = "persoana_contact")
    private String contact;

    @Column(name = "email")
    private String email;

    @Column(name = "telefon_client")
    private String telefon;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    @ToString.Exclude()
    private List<Cursa> curse;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @ToString.Exclude()
    private Company company;
}
