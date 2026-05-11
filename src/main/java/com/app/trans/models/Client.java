package com.app.trans.models;

import com.app.trans.dtos.ClientDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "client")
@Setter
@Getter
@ToString(exclude = { "curse" })
@EqualsAndHashCode
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "id_client")
    private long id;

    // @NotBlank(message = "Nume cannot be null or blank")
    @Column(name = "nume_client")
    private String nume;

    // @NotBlank(message = "Cui cannot be null or blank")
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

    // @NotBlank(message = "Telefon cannot be null or blank")
    @Column(name = "telefon_client")
    private String telefon;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Cursa> curse;

    public Client() {
    }

//    public Client(long id, String nume, String cui, String adresaFacturare, String adresaCorespondenta, String cod,
//            String cont, String banca, String contact, String email, String telefon) {
//        this.id = id;
//        this.nume = nume;
//        this.cui = cui;
//        this.adresaFacturare = adresaFacturare;
//        this.adresaCorespondenta = adresaCorespondenta;
//        this.cod = cod;
//        this.cont = cont;
//        this.banca = banca;
//        this.contact = contact;
//        this.email = email;
//        this.telefon = telefon;
//    }

    // Constructor from ClientDTO
    public Client(ClientDTO clientDTO) {
        this.nume = clientDTO.getNume();
        this.cui = clientDTO.getCui();
        this.adresaFacturare = clientDTO.getAdresaFacturare();
        this.adresaCorespondenta = clientDTO.getAdresaCorespondenta();
        this.cod = clientDTO.getCod();
        this.cont = clientDTO.getCont();
        this.banca = clientDTO.getBanca();
        this.contact = clientDTO.getContact();
        this.email = clientDTO.getEmail();
        this.telefon = clientDTO.getTelefon();
    }
}
