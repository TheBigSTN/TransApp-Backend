package com.app.trans.mappers;

import com.app.trans.dtos.CursaDTO;
import com.app.trans.models.Cursa;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CursaDTOMapper implements Function<Cursa, CursaDTO> {

    @Override
    public CursaDTO apply(Cursa cursa) {
        CursaDTO cursaDTO = new CursaDTO();
        cursaDTO.setId(cursa.getId());
        cursaDTO.setKm(cursa.getKm());
        cursaDTO.setDataEfectuare(cursa.getDataEfectuare());
        cursaDTO.setIdMasina(cursa.getMasina().getId());
        cursaDTO.setIdSofer(cursa.getSofer().getId());
        cursaDTO.setIdClient(cursa.getClient().getId());
        cursaDTO.setIdAnexa(cursa.getAnexa() != null ? cursa.getAnexa().getId() : null);
        cursaDTO.setMasinaNumar(cursa.getMasina().getNumar());
        cursaDTO.setNumeSofer(cursa.getSofer().getNume() + " " + cursa.getSofer().getPrenume());
        cursaDTO.setNumeClient(cursa.getClient().getNume());
        cursaDTO.setLivrare(cursa.getLivrare());
        cursaDTO.setTarif(cursa.getTarif());
        return cursaDTO;
    }
}
