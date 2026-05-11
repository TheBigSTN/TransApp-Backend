package com.app.trans.mappers;

import com.app.trans.dtos.SoferDTO;
import com.app.trans.models.Sofer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SoferDTOMapper implements Function<Sofer, SoferDTO> {

    @Override
    public SoferDTO apply(Sofer sofer) {
        return new SoferDTO(
                sofer.getId(),
                sofer.getNume(),
                sofer.getPrenume(),
                sofer.getDataNastere(),
                sofer.getCnp(),
                sofer.getSeriePermis(),
                sofer.getDataEmiterePermis(),
                sofer.getDataExpirarePermis(),
                sofer.getAdresa(),
                sofer.getTelefon(),
                sofer.getEmail()
        );
    }
}
