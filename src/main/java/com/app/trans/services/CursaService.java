package com.app.trans.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.app.trans.repos.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.app.trans.dtos.CursaDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.CursaDTOMapper;
import com.app.trans.models.Anexa;
import com.app.trans.models.Client;
import com.app.trans.models.Cursa;
import com.app.trans.models.Masina;
import com.app.trans.models.Sofer;
import com.app.trans.models.enums.TipAnexa;
import com.app.trans.repos.CursaRepo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CursaService {

    private final CursaRepo cursaRepo;
    private final CursaDTOMapper cursaDTOMapper;
    private final MasinaService masinaService;
    private final SoferService soferService;
    private final ClientService clientService;
    private final AnexaService anexaService;
    private final CompanyRepo companyRepo;

    @Transactional
    public CursaDTO addCursa(CursaDTO cursaDTO, UUID companyId) {
        Cursa cursa = Cursa.builder()
                .km(cursaDTO.getKm())
                .dataEfectuare(cursaDTO.getDataEfectuare())
                .livrare(cursaDTO.getLivrare())
                .tarif(cursaDTO.getTarif())
                .masina(masinaService.getMasinaEntityById(cursaDTO.getIdMasina(), companyId))
                .client(clientService.getClientEntityById(cursaDTO.getIdClient()))
                .sofer(soferService.getSoferEntityById(cursaDTO.getIdSofer(), companyId))
                .company(companyRepo.getReferenceById(companyId))
                .build();

        if (cursaDTO.getIdAnexa() != null) {
            Anexa anexa = anexaService.getAnexaEntityByIdAndCompanyId(cursaDTO.getIdAnexa(), companyId);
            if (anexa.getTipAnexa().equals(TipAnexa.NEVALIDATA)) {
                cursa.setAnexa(anexa);
            } else {
                log.warn("Anexa este deja VALIDATA, cursa nu poate fi asociata ei");
            }
        }

        Cursa savedCursa = cursaRepo.save(cursa);
        return cursaDTOMapper.apply(savedCursa);
    }

//    @Transactional
//    public boolean setCursaListToAnexa(long[] cursaIds, Anexa anexa) {
//        try {
//            for (long id : cursaIds) {
//                Cursa cursa = getCursaEntityById(id);
//                cursa.setAnexa(anexa);
//                cursaRepo.save(cursa);
//            }
//            return true;
//        } catch (Exception e) {
//            log.error("Exception while seting the anexes to the Curse");
//            return false;
//        }
//    }

    public List<CursaDTO> getAllCursa(UUID companyId) {
        return cursaRepo.findAllByCompanyId(companyId)
                .stream()
                .map(cursaDTOMapper)
                .collect(Collectors.toList());
    }

    public List<CursaDTO> getCursaPerioada(LocalDate startDate, LocalDate endDate, UUID companyId) {
        return cursaRepo.findAllByPeriodAndCompanyId(startDate, endDate, companyId)
                .stream()
                .map(cursaDTOMapper)
                .collect(Collectors.toList());
    }

    public CursaDTO getCursaById(long id, UUID companyId) {
        Cursa cursa = cursaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
            new ResourceNotFoundException("Cursa Not Found with ID: " + id));

        return cursaDTOMapper.apply(cursa);
    }

    @Transactional
    public void deleteCursa(long id, UUID companyId) {
        Cursa cursa = cursaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Cursa Not Found with ID: " + id));

        cursaRepo.delete(cursa);
    }

    @Transactional
    public CursaDTO updateCursa(long id, CursaDTO newCursa, UUID companyId) {
        Cursa cursa = cursaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Cursa Not Found with ID: " + id));

        cursa.setKm(newCursa.getKm());
        cursa.setDataEfectuare(newCursa.getDataEfectuare());
        cursa.setLivrare(newCursa.getLivrare());
        cursa.setTarif(newCursa.getTarif());

        if (cursa.getMasina().getId() != newCursa.getIdMasina()) {
            Masina newMasina = masinaService.getMasinaEntityById(newCursa.getIdMasina(), companyId);
            cursa.setMasina(newMasina);
        }
        if (cursa.getSofer().getId() != newCursa.getIdSofer()) {
            Sofer newSofer = soferService.getSoferEntityById(newCursa.getIdSofer(), companyId);
            cursa.setSofer(newSofer);
        }
        if (cursa.getClient().getId() != newCursa.getIdClient()) {
            Client newClient = clientService.getClientEntityById(newCursa.getIdClient());
            cursa.setClient(newClient);
        }
        
        if (newCursa.getIdAnexa() != null && (cursa.getAnexa() == null || cursa.getAnexa().getId() != newCursa.getIdAnexa())) {
            Anexa newAnexa = anexaService.getAnexaEntityByIdAndCompanyId(newCursa.getIdAnexa(), companyId);
            if (newAnexa.getTipAnexa().equals(TipAnexa.NEVALIDATA)) {
                cursa.setAnexa(newAnexa);
            } else {
                log.warn("Anexa este deja VALIDATA, cursa nu poate fi asociata ei");
            }
        } else if (newCursa.getIdAnexa() == null) {
            cursa.setAnexa(null);
        }

        Cursa savedCursa = cursaRepo.save(cursa);
        return cursaDTOMapper.apply(savedCursa);
    }

}
