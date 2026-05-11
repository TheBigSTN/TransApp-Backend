package com.app.trans.mappers;

import java.util.function.Function;

import com.app.trans.models.Alimentare;
import com.app.trans.dtos.AlimentareDTO;

import org.springframework.stereotype.Service;

@Service
public class AlimentareDTOMapper implements Function<Alimentare, AlimentareDTO> {

    @Override
    public AlimentareDTO apply(Alimentare alimentare) {
        return new AlimentareDTO(
            alimentare.getId(),
            alimentare.getMasina().getId(), // Assuming Masina has getId method
            alimentare.getMasina().getNumar(), // Assuming Masina has getNumar method
            alimentare.getData_alimentare(),
            alimentare.getLitri(),
            alimentare.getPret_unitar(),
            alimentare.getTip()
        );
    }

}
