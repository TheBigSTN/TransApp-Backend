package com.app.trans.models;

import com.app.trans.dtos.CursaDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;



@Entity
@Table(name = "cursa")
@Getter
@Setter
@ToString (exclude = {"anexa", "sofer", "masina", "client"})
@EqualsAndHashCode
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
    @JoinColumn(name = "id_masina")
    private Masina masina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sofer", nullable = false)
    private Sofer sofer;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_anexa", nullable = true)
    private Anexa anexa;

    public Cursa(){
    }

    public Cursa(long id, Masina masina, Integer km) {
        this.id = id;
        this.masina = masina;
        this.km = km;
    }
    
    public Cursa(CursaDTO cursaDTO) {
        this.id = cursaDTO.getId();
        this.km = cursaDTO.getKm();
        this.dataEfectuare = cursaDTO.getDataEfectuare();
        this.livrare = cursaDTO.getLivrare();
        this.tarif = cursaDTO.getTarif();
        
        
        this.masina = new Masina(); // Sau folosiți constructorul corespunzător
        this.sofer = new Sofer();
        this.client = new Client();
        
        // Set IDs for Masina, Sofer, and Client
        this.masina.setId(cursaDTO.getIdMasina());
        this.sofer.setId(cursaDTO.getIdSofer());
        this.client.setId(cursaDTO.getIdClient());
        
        // Set Anexa ID if present
        if (cursaDTO.getIdAnexa() != null) {
            this.anexa.setId(cursaDTO.getIdAnexa());
        }
    }

    public Cursa(long id, Masina masina, Integer km, LocalDate dataEfectuare, String livrare, Float tarif, Sofer sofer, Client client, Anexa anexa) {
        this.id = id;
        this.masina = masina;
        this.km = km;
        this.dataEfectuare = dataEfectuare;
        this.livrare = livrare;
        this.tarif = tarif;
        this.sofer = sofer;
        this.client = client;
        this.anexa = anexa;
    }


}
