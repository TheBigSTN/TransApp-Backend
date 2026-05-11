package com.app.trans.services;

import com.app.trans.dtos.AnexaDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.AnexaDTOMapper;
import com.app.trans.models.Anexa;
import com.app.trans.models.Cursa;
import com.app.trans.models.enums.TipAnexa;
import com.app.trans.repos.AnexaRepo;
import com.app.trans.repos.CompanyRepo;
import com.app.trans.repos.CursaRepo;
import com.app.trans.util.CursaUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.core.io.InputStreamResource;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnexaService {

	private final AnexaRepo anexaRepo;
	private final CursaRepo cursaRepo;
	private final AnexaDTOMapper anexaDTOMapper;
	private final CursaUtil cursaUtil;
	private final CompanyRepo companyRepo;

	@Transactional
	public AnexaDTO addAnexa(List<Long> cursaIds, UUID companyId) {
		Anexa anexa = Anexa.builder()
				.tipAnexa(TipAnexa.NEVALIDATA)
				.company(companyRepo.getReferenceById(companyId))
				.build();

		Anexa savedAnexa = anexaRepo.save(anexa);

		List<Cursa> curse = cursaIds.stream()
				.map(id -> cursaRepo.findByIdAndCompanyId(id, companyId)
						.orElseThrow(() -> new ResourceNotFoundException("Cursa Not Found with ID: " + id)))
				.peek(cursa -> {
					if (cursa.getAnexa() != null) {
						throw new RuntimeException("Cursa with ID " + cursa.getId() + " is already assigned to Anexa " + cursa.getAnexa().getId());
					}
					cursa.setAnexa(savedAnexa);
				})
				.collect(Collectors.toList());

		cursaRepo.saveAll(curse);
		savedAnexa.setCurse(curse);

		return anexaDTOMapper.apply(savedAnexa);
	}

	public List<AnexaDTO> getAllAnexa(UUID companyId) {
		return anexaRepo.findAllByCompanyId(companyId)
				.stream()
				.map(anexaDTOMapper)
				.collect(Collectors.toList());
	}

	public AnexaDTO getAnexaByIdAndCompanyId(long id, UUID companyId) {
		Anexa anexa = anexaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
				new ResourceNotFoundException("Anexa Not Found with ID: " + id));
		return anexaDTOMapper.apply(anexa);
	}

	public Anexa getAnexaEntityByIdAndCompanyId(long id, UUID companyId) {
		return anexaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
				new ResourceNotFoundException("Anexa Not Found with ID: " + id));
	}

	@Transactional
	public void deleteAnexa(long id, UUID companyId) {
		Anexa anexa = anexaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
				new ResourceNotFoundException("Anexa Not Found with ID: " + id));
		
		if (anexa.getCurse() != null) {
			anexa.getCurse().forEach(cursa -> cursa.setAnexa(null));
			cursaRepo.saveAll(anexa.getCurse());
		}
		
		anexaRepo.delete(anexa);
	}

	@Transactional
	public Anexa validareAnexa(long id, UUID companyId) {
		Anexa anexa = getAnexaEntityByIdAndCompanyId(id, companyId);
		List<Cursa> cursaList = anexa.getCurse();

		if (cursaList == null || cursaList.isEmpty()) {
			log.error("Anexa {} does not have any Curse, it cannot be VALIDATED", id);
			throw new RuntimeException("Anexa does not have any Curse, it cannot be VALIDATED");
		}

		if (anexa.getTipAnexa().equals(TipAnexa.NEVALIDATA)) {
			if (cursaUtil.checkSameClient(cursaList) == null) {
				log.error("Anexa {} could not be validated: different clients in curse list", id);
				throw new RuntimeException("Anexa nu a putut fi validata, cursele nu apartin aceluiasi client!");
			}

			anexa.setKmTotal(cursaUtil.calculateKmTotal(cursaList));
			anexa.setTarifMediu(cursaUtil.calculateTarifMediu(cursaList));
			anexa.setTva(cursaUtil.calculateTva(cursaList));
			anexa.setValoare(cursaUtil.calculateValoare(cursaList));
			anexa.setTipAnexa(TipAnexa.VALIDATA);
			return anexaRepo.save(anexa);

		} else {
			// Invalidate logic: Reset calculated values
			anexa.setKmTotal(0);
			anexa.setTarifMediu(0f);
			anexa.setTva(0f);
			anexa.setValoare(0f);
			anexa.setTipAnexa(TipAnexa.NEVALIDATA);
			return anexaRepo.save(anexa);
		}
	}

	public InputStreamResource generateAnexa(long id, UUID companyId) {
		Anexa anexa = getAnexaEntityByIdAndCompanyId(id, companyId);
		try {
			return cursaUtil.generatorPdfItext(anexa);
		} catch (Exception e) {
			log.error("Anexa generation failed for ID {}: {}", id, e.getMessage());
			throw new RuntimeException("PDF generation failed");
		}
	}
}
