package com.app.trans.dtos;

import com.app.trans.models.enums.StatusMasina;
import com.app.trans.models.enums.TipMasina;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MasinaDTO {

    private long id;

    private String numar;

    private String serie;

    private TipMasina tipauto;

    private Integer capacitateTransport;

    private Integer capacitateCombustibil;

    private StatusMasina status;

    private List<Long> curse;

    private List<Long> alimentari;

    private List<Long> licente;

    public MasinaDTO(
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
        this.capacitateTransport = capacitateTransport;
        this.capacitateCombustibil = capacitateCombustibil;
        this.tipauto = tipauto;
        this.status = status;
    }

}