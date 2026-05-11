package com.app.trans.mappers;

import java.util.function.Function;


import org.springframework.stereotype.Service;

import com.app.trans.dtos.LicentaDTO;
import com.app.trans.models.Licenta;

@Service
public class LicentaDTOMapper implements Function<Licenta, LicentaDTO> {

    @Override
    public LicentaDTO apply(Licenta licenta) {
        return new LicentaDTO(
            licenta.getId(),
            licenta.getMasina().getId(), // Assuming Masina has getId method
            licenta.getMasina().getNumar(), // Assuming Masina has getNumar method
            licenta.getSerie(),
            licenta.getTip(),
            licenta.getData_inceput(),
            licenta.getData_final(),
            licenta.getPret()
        );
    }

}
