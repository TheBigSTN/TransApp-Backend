package com.app.trans.mappers;

import com.app.trans.dtos.AnexaDTO;
import com.app.trans.models.Anexa;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AnexaDTOMapper implements Function<Anexa, AnexaDTO> {

    @Override
    public AnexaDTO apply(Anexa anexa) {
        AnexaDTO anexaDTO = new AnexaDTO();
        anexaDTO.setId(anexa.getId());
        anexaDTO.setTipAnexa(anexa.getTipAnexa());
        anexaDTO.setKmTotal(anexa.getKmTotal());
        anexaDTO.setTarifMediu(anexa.getTarifMediu());
        anexaDTO.setValoare(anexa.getValoare());
        anexaDTO.setTva(anexa.getTva());
        if(anexa.getCurse()!= null && !anexa.getCurse().isEmpty()) {
	        anexaDTO.setIdClient(anexa.getCurse().get(0).getClient().getId());
	        anexaDTO.setNumeClient(anexa.getCurse().get(0).getClient().getNume());
        }
        return anexaDTO;
    }
}
