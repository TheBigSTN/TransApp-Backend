package com.app.trans.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public CursaDTO addCursa(CursaDTO cursaDTO) {
        Cursa cursa = new Cursa(cursaDTO);
        cursa.setMasina(masinaService.getMasinaEntityById(cursaDTO.getIdMasina()));
        cursa.setClient(clientService.getClientEntityById(cursaDTO.getIdClient()));
        cursa.setSofer(soferService.getSoferEntityById(cursaDTO.getIdSofer()));

        if (cursaDTO.getIdAnexa() != null) {
            Anexa anexa = anexaService.getAnexaEntityById(cursaDTO.getIdAnexa());
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

    public List<CursaDTO> getAllCursa() {
        return cursaRepo.findAll()
                .stream()
                .map(cursaDTOMapper)
                .collect(Collectors.toList());
    }

    public List<CursaDTO> getCursaPerioada(LocalDate startDate, LocalDate endDate) {
        return cursaRepo.findAllByPeriod(startDate, endDate)
                .stream()
                .map(cursaDTOMapper)
                .collect(Collectors.toList());
    }

    public CursaDTO getCursaById(long id) {
        Cursa cursa = cursaRepo.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Cursa Not Found with ID: " + id));

        return cursaDTOMapper.apply(cursa);
    }

//    public Cursa getCursaEntityById(long id) {
//        return cursaRepo.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Cursa Not Found with ID: " + id));
//    }

    @Transactional
    public void deleteCursa(long id) {
        Cursa cursa = cursaRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cursa Not Found with ID: " + id));

        cursaRepo.delete(cursa);
    }

    @Transactional
    public CursaDTO updateCursa(long id, CursaDTO newCursa) {
        Cursa cursa = cursaRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cursa Not Found with ID: " + id));

        cursa.setKm(newCursa.getKm());
        cursa.setDataEfectuare(newCursa.getDataEfectuare());

        if (cursa.getMasina().getId() != newCursa.getIdMasina()) {
            Masina newMasina = masinaService.getMasinaEntityById(newCursa.getIdMasina());
            cursa.setMasina(newMasina);
        }
        if (cursa.getSofer().getId() != newCursa.getIdSofer()) {
            Sofer newSofer = soferService.getSoferEntityById(newCursa.getIdSofer());
            cursa.setSofer(newSofer);
        }
        if (cursa.getClient().getId() != newCursa.getIdClient()) {
            Client newClient = clientService.getClientEntityById(newCursa.getIdClient());
            cursa.setClient(newClient);
        }
        if (cursa.getAnexa().getId() != newCursa.getIdAnexa()) {
            Anexa newAnexa = anexaService.getAnexaEntityById(newCursa.getIdAnexa());
            if (newAnexa.getTipAnexa().equals(TipAnexa.NEVALIDATA)) {
                cursa.setAnexa(newAnexa);
            } else {
                log.warn("Anexa este deja VALIDATA, cursa nu poate fi asociata ei");
            }
        }
        Cursa savedCursa = cursaRepo.save(cursa);
        return cursaDTOMapper.apply(savedCursa);
    }

}
