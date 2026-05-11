package com.app.trans.models;

import jakarta.persistence.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

import com.app.trans.models.enums.StatusMasina;
import com.app.trans.models.enums.TipMasina;

@Entity
@Table(name = "masina")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Masina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "id_masina")
    private long id;

    @Column(name = "tip_auto")
    @Enumerated(EnumType.STRING)
    private TipMasina tipauto;

    @Column(name = "numar_masina")
    private String numar;

    @Column(name = "serie_masina")
    private String serie;

    @Column(name = "capacitate_transport")
    private Integer capacitateTransport;

    @Column(name = "capacitate_combustibil")
    private Integer capacitateCombustibil;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusMasina status;

    @JsonIgnore
    @ToString.Exclude()
    @OneToMany(mappedBy = "masina")
    private List<Cursa> curse;

    @JsonIgnore
    @ToString.Exclude()
    @OneToMany(mappedBy = "masina")
    private List<Alimentare> alimentari;

    @JsonIgnore
    @ToString.Exclude()
    @OneToMany(mappedBy = "masina")
    private List<Licenta> licente;

    @ManyToOne
    @ToString.Exclude()
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

}
