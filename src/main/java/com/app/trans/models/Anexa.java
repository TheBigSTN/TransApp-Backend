package com.app.trans.models;

import com.app.trans.models.enums.TipAnexa;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import lombok.ToString;

@Entity
@Table(name = "anexa")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Anexa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anexa")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_anexa")
    private TipAnexa tipAnexa;

    @Column(name = "km_total")
    private Integer kmTotal;

    @Column(name = "tarif_mediu")
    private Float tarifMediu;

    @Column(name = "valoare")
    private Float valoare;

    @Column(name = "tva")
    private Float tva;
    
    @JsonIgnore
    @OneToMany(mappedBy = "anexa")
    private List<Cursa> curse;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}

    
 
  