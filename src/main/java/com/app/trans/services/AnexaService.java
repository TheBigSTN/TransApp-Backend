package com.app.trans.services;

import com.app.trans.dtos.AnexaDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.AnexaDTOMapper;
import com.app.trans.models.Anexa;
import com.app.trans.models.Cursa;
import com.app.trans.models.enums.TipAnexa;
import com.app.trans.repos.AnexaRepo;
import com.app.trans.util.CursaUtil;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.core.io.InputStreamResource;

@Service
@Slf4j
public class AnexaService {

	@Autowired
	private AnexaRepo anexaRepo;

	@Autowired
	private AnexaDTOMapper anexaDTOMapper;

	@Autowired
	private CursaUtil cursaUtil;

	public boolean test() {
		log.info("in the test service");
		return true;
	}

	@Transactional
	public Anexa addAnexa() {
		Anexa anexa = new Anexa(); // Use the apply method
		anexa.setTipAnexa(TipAnexa.NEVALIDATA);
		return anexaRepo.save(anexa);
	}

	public List<AnexaDTO> getAllAnexa() {
		return anexaRepo.findAll()
				.stream()
				.map(anexaDTOMapper)
				.collect(Collectors.toList());
	}

	public AnexaDTO getAnexaById(long id) {
		Optional<Anexa> optionalAnexa = anexaRepo.findById(id);
		if (!optionalAnexa.isPresent()) {
			throw new ResourceNotFoundException("Anexa Not Found with ID: " + id);
		}
		return anexaDTOMapper.apply(optionalAnexa.get());
	}

	public Anexa getAnexaEntityById(long id) {
		log.info("I am getting the anexa from id", id);
		Optional<Anexa> optionalAnexa = anexaRepo.findById(id);
		if (!optionalAnexa.isPresent()) {
			throw new ResourceNotFoundException("Anexa Not Found with ID: " + id);
		}
		return optionalAnexa.get();
	}

	@Transactional
	public void deleteAnexa(long id) {
		Optional<Anexa> optionalAnexa = anexaRepo.findById(id);
		if (!optionalAnexa.isPresent()) {
			throw new ResourceNotFoundException("Anexa Not Found with ID: " + id);
		}
		Anexa anexa = optionalAnexa.get();
		anexaRepo.delete(anexa);
	}
	// nu se poate face update (id nu se poate schimba si VALIDAREA/INVALIDAREA se
	// face prin alta functie
	// iar celelelalte valori sunt modificate automat pe baza curselor c
	// @Transactional
	// public AnexaDTO updateAnexa(long id, AnexaDTO newAnexa) {
	// Optional<Anexa> optionalAnexa = anexaRepo.findById(id);
	// if (!optionalAnexa.isPresent()) {
	// throw new ResourceNotFoundException("Anexa Not Found with ID: " + id);
	// }
	//
	// Anexa anexa = optionalAnexa.get();
	// if(anexa.getTipAnexa().equals(TipAnexa.VALIDATA)) {
	// log.console("Anexa VALIDATA ");
	// }
	// // Update properties of the Anexa entity with the values from newAnexa DTO
	// // Similar to what you did in updateCursa method in CursaService
	//
	// Anexa savedAnexa = anexaRepo.save(anexa);
	// return anexaDTOMapper.apply(savedAnexa);
	// }

	// public Anexa validareAnexa(long id) {
	// log.error("in the service");
	// Anexa anexa = new Anexa();
	// anexa.setId(1);
	// return anexa;
	// }

	// check that the second if will not be called after NEVALIDATA update in the
	// first if, it should not because of the else
	public Anexa validareAnexa(long id) throws Exception {
		log.info("id -ul o merge?");
		try {
			log.info("in the service <" + id + ">");
			Anexa anexa = getAnexaEntityById(id);
			Anexa initialAnexa = anexa;
			log.info(anexa.toString());
			List<Cursa> cursaList = anexa.getCurse();
			if (cursaList == null || cursaList.isEmpty()) {
				log.error("Anexa does not have any Curse, it can not be VALIDATED");
				return anexa;
			} else {
				log.info("in the else <" + id + ">");
				if (anexa.getTipAnexa().equals(TipAnexa.NEVALIDATA)) {
					try {

						if (cursaUtil.checkSameClient(cursaList) == null) {
							log.error("Anexa nu a putut fi validata, cursele nu apartin aceluiasi client!");
							throw new Exception("Anexa nu a putut fi validata, cursele nu apartin aceluiasi client!");
						} else {
							anexa.setKmTotal(cursaUtil.calculateKmTotal(cursaList));
							anexa.setTarifMediu(cursaUtil.calculateTarifMediu(cursaList));
							anexa.setTva(cursaUtil.calculateTva(cursaList));
							anexa.setValoare(cursaUtil.calculateValoare(cursaList));
							anexa.setTipAnexa(TipAnexa.VALIDATA);
							log.info("Anexa before saving " + anexa.toString());
							return anexaRepo.save(anexa);
						}

					} catch (Exception e) {
						throw new Exception("Something when wrong while VALIDATING the anexa");
					}
				} else if (anexa.getTipAnexa().equals(TipAnexa.VALIDATA)) {

					try {
						Anexa emptyAnexa = new Anexa();
						emptyAnexa.setId(initialAnexa.getId());
						emptyAnexa.setTipAnexa(TipAnexa.NEVALIDATA);
						return anexaRepo.save(emptyAnexa);
					} catch (Exception e) {
						throw new Exception("Something when wrong while INVALIDATING the anexa");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("nu mergeeeeee");
		}
		Anexa anexa = new Anexa();
		anexa.setId(0);
		return anexa; // initialAnexa;
	}

	// @Transactional
	// public Anexa validareAnexa(long id) {
	// try {
	// log.info("Validating Anexa with ID: {}", id);
	//
	// // Retrieve the Anexa entity by ID
	// Anexa anexa = getAnexaEntityById(id);
	//
	// // Check if the retrieved Anexa is null
	// if (anexa == null) {
	// log.error("Anexa not found with ID: {}", id);
	// throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anexa not found with
	// ID: " + id);
	// }
	//
	// // Get the list of curse from the Anexa entity
	// List<Cursa> cursaList = anexa.getCurse();
	//
	// // Check if the curse list is null or empty
	// if (cursaList == null || cursaList.isEmpty()) {
	// log.error("Anexa with ID {} does not have any Curse, it cannot be VALIDATED",
	// id);
	// throw new ValidationException("Anexa does not have any Curse, it cannot be
	// VALIDATED");
	// }
	//
	// // Check the TipAnexa of the Anexa entity
	// if (anexa.getTipAnexa().equals(TipAnexa.NEVALIDATA)) {
	// // Perform validation logic
	// // For example:
	// // anexa.setKmTotal(cursaUtil.calculateKmTotal(cursaList));
	// // anexa.setTarifMediu(cursaUtil.calculateTarifMediu(cursaList));
	// // ...
	//
	// // Set TipAnexa to VALIDATA
	// anexa.setTipAnexa(TipAnexa.VALIDATA);
	//
	// // Save the updated Anexa entity
	// return anexaRepo.save(anexa);
	// } else if (anexa.getTipAnexa().equals(TipAnexa.VALIDATA)) {
	// // Perform invalidation logic
	// // Create a new Anexa entity with TipAnexa NEVALIDATA
	// Anexa emptyAnexa = new Anexa();
	// emptyAnexa.setId(anexa.getId());
	// emptyAnexa.setTipAnexa(TipAnexa.NEVALIDATA);
	//
	// // Save the new Anexa entity
	// return anexaRepo.save(emptyAnexa);
	// } else {
	// log.error("Anexa with ID {} has an invalid TipAnexa: {}", id,
	// anexa.getTipAnexa());
	// throw new ValidationException("Anexa has an invalid TipAnexa: " +
	// anexa.getTipAnexa());
	// }
	// } catch (ResponseStatusException e) {
	// // Log the error and rethrow the exception
	// log.error(e.getMessage());
	// throw e;
	// } catch (ValidationException e) {
	// // Log the error and rethrow the exception
	// log.error(e.getMessage());
	// throw e;
	// } catch (Exception e) {
	// // Log the error and throw a new Exception
	// log.error("An error occurred while validating Anexa with ID: {}", id, e);
	// throw new RuntimeException("An error occurred while validating Anexa");
	// }
	// }

	public InputStreamResource generateAnexa(long id) {
		Anexa anexa = getAnexaEntityById(id);
		try {
			return cursaUtil.generatorPdfItext(anexa);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Anexa generation did not worked" + e);
			return null;
		}
	}

}
