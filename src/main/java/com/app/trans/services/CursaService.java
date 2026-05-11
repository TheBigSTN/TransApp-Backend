package com.app.trans.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class CursaService {

    @Autowired
    private CursaRepo cursaRepo;

    @Autowired
    private CursaDTOMapper cursaDTOMapper;

    @Autowired
    private MasinaService masinaService;

    @Autowired
    private SoferService soferService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AnexaService anexaService;

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

    @Transactional
    public boolean setCursaListToAnexa(long[] cursaIds, Anexa anexa) {
        try {
            for (long id : cursaIds) {
                Cursa cursa = getCursaEntityById(id);
                cursa.setAnexa(anexa);
                cursaRepo.save(cursa);
            }
            return true;
        } catch (Exception e) {
            log.error("Exception while seting the anexes to the Curse");
            return false;
        }
    }

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
        Optional<Cursa> optionalCursa = cursaRepo.findById(id);
        if (!optionalCursa.isPresent()) {
            throw new ResourceNotFoundException("Cursa Not Found with ID: " + id);
        }
        return cursaDTOMapper.apply(optionalCursa.get());
    }

    public Cursa getCursaEntityById(long id) {
        Optional<Cursa> optionalCursa = cursaRepo.findById(id);
        if (!optionalCursa.isPresent()) {
            throw new ResourceNotFoundException("Cursa Not Found with ID: " + id);
        }
        return optionalCursa.get();
    }

    @Transactional
    public void deleteCursa(long id) {
        Optional<Cursa> optionalCursa = cursaRepo.findById(id);
        if (!optionalCursa.isPresent()) {
            throw new ResourceNotFoundException("Cursa Not Found with ID: " + id);
        }
        Cursa cursa = optionalCursa.get();
        cursaRepo.delete(cursa);
    }

    @Transactional
    public CursaDTO updateCursa(long id, CursaDTO newCursa) {
        Optional<Cursa> optionalCursa = cursaRepo.findById(id);
        if (!optionalCursa.isPresent()) {
            throw new ResourceNotFoundException("Cursa Not Found with ID: " + id);
        }

        Cursa cursa = optionalCursa.get();
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
