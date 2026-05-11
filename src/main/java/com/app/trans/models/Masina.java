package com.app.trans.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import com.app.trans.dtos.MasinaDTO;
import com.app.trans.models.enums.StatusMasina;
import com.app.trans.models.enums.TipMasina;

//import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "masina")
@Setter
@Getter
@ToString(exclude = { "curse" })
@EqualsAndHashCode
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

    @OneToMany(mappedBy = "masina")
    private List<Cursa> curse;

    @OneToMany(mappedBy = "masina")
    private List<Alimentare> alimentari;

    @OneToMany(mappedBy = "masina")
    private List<Licenta> licente;

    public Masina() {
    }

    public Masina(
            long id,
            String numar,
            String serie,
            Integer capacitateTransport,
            Integer capacitateCombustibil,
            TipMasina tipauto,
            StatusMasina status) {
        this.id = id;
        this.numar = numar;
        this.serie = serie;
        this.tipauto = tipauto;
        this.capacitateTransport = capacitateTransport;
        this.capacitateCombustibil = capacitateCombustibil;
        this.status = status;
    }

    public Masina(MasinaDTO masina) {
        this.id = masina.getId();
        this.numar = masina.getNumar();
        this.serie = masina.getSerie();
        this.capacitateTransport = masina.getCapacitateTransport();
        this.capacitateCombustibil = masina.getCapacitateCombustibil();
        this.tipauto = masina.getTipauto();
        this.status = masina.getStatus();
    }
}
