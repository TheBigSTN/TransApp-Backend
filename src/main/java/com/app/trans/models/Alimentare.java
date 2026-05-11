package com.app.trans.models;

import com.app.trans.dtos.AlimentareDTO;
import com.app.trans.models.enums.TipAlimentare;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(exclude = { "masina" })
@EqualsAndHashCode
@Table(name = "alimentare")
public class Alimentare {

    @Id
    @Column(name = "id_alimentare")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_masina")
    private Masina masina;

    @Column(name = "data_alimentare")
    private LocalDate data_alimentare;

    @Column(name = "numar_litri")
    private Float litri;

    @Column(name = "pret_unitar")
    private Float pret_unitar;

    @Column(name = "tip")
    @Enumerated(EnumType.STRING)
    private TipAlimentare tip;

    public Alimentare() {
    }

    public Alimentare(long id, Masina masina, LocalDate data_alimentare, Float litri, Float pret_unitar,
            TipAlimentare tip) {
        this.id = id;
        this.masina = masina;
        this.data_alimentare = data_alimentare;
        this.litri = litri;
        this.pret_unitar = pret_unitar;
        this.tip = tip;
    }

    public Alimentare(AlimentareDTO alimentareDTO) {
        this.id = alimentareDTO.getId();
        this.masina.setId(alimentareDTO.getMasinaId());
        this.data_alimentare = alimentareDTO.getDataAlimentare();
        this.litri = alimentareDTO.getLitri();
        this.pret_unitar = alimentareDTO.getPretUnitar();
        this.tip = alimentareDTO.getTip();
    }

}
