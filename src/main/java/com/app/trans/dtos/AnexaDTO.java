package com.app.trans.dtos;

import com.app.trans.models.enums.TipAnexa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnexaDTO {

    private long id;
    private TipAnexa tipAnexa;
    private Integer kmTotal;
    private Float tarifMediu;
    private Float valoare;
    private Float tva;
    private Long idClient;
    private String numeClient;
    
}
