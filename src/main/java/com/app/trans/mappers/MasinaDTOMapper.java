package com.app.trans.mappers;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.app.trans.dtos.MasinaDTO;
import com.app.trans.models.Masina;

@Service
public class MasinaDTOMapper implements Function<Masina, MasinaDTO> {

	@Override
	public MasinaDTO apply(Masina masina) {
		return new MasinaDTO(
				masina.getId(),
				masina.getNumar(),
				masina.getSerie(),
				masina.getCapacitateTransport(),
				masina.getCapacitateCombustibil(),
				masina.getTipauto(),
				masina.getStatus());
	}

}
