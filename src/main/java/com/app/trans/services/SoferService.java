package com.app.trans.services;


import com.app.trans.dtos.SoferDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.SoferDTOMapper;
import com.app.trans.models.Sofer;
import com.app.trans.repos.SoferRepo;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SoferService {

    private final SoferRepo soferRepo;
    private final SoferDTOMapper soferDTOMapper;

    @Transactional
    public SoferDTO addSofer(SoferDTO soferDTO) {
        Sofer sofer = new Sofer(soferDTO);
        Sofer savedSofer = soferRepo.save(sofer);
        return soferDTOMapper.apply(savedSofer);
    }

    public SoferDTO getSoferById(Long id) {
        Optional<Sofer> optionalSofer = soferRepo.findById(id);
        Sofer sofer = optionalSofer.orElseThrow(() -> new ResourceNotFoundException("Sofer Not Found with ID: " + id));
        return soferDTOMapper.apply(sofer);
    }
    
    public Sofer getSoferEntityById(long id) {
    	Optional<Sofer> optionalSofer = soferRepo.findById(id);
        return optionalSofer.orElseThrow(() -> new ResourceNotFoundException("Sofer Not Found with ID: " + id));
	}

    public List<SoferDTO> getAllSoferDTO() {
        List<Sofer> soferList = soferRepo.findAll();
        return soferList.stream()
                .map(soferDTOMapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSoferById(Long id) {
        Optional<Sofer> optionalSofer = soferRepo.findById(id);
        if (!optionalSofer.isPresent()) {
            throw new ResourceNotFoundException("Sofer Not Found with ID: " + id);
        }
        Sofer sofer = optionalSofer.get();
        soferRepo.delete(sofer);
    }

    @Transactional
    public void updateSoferById(Long id, SoferDTO newSoferDTO) {
        Optional<Sofer> optionalSofer = soferRepo.findById(id);
        if (!optionalSofer.isPresent()) {
            throw new ResourceNotFoundException("Sofer Not Found with ID: " + id);
        }
        Sofer sofer = optionalSofer.get();
        
        // Update Sofer properties using SoferDTO
        sofer.setNume(newSoferDTO.getNume());
        sofer.setPrenume(newSoferDTO.getPrenume());
        sofer.setDataNastere(newSoferDTO.getDataNastere());
        sofer.setCnp(newSoferDTO.getCnp());
        sofer.setSeriePermis(newSoferDTO.getSeriePermis());
        sofer.setDataEmiterePermis(newSoferDTO.getDataEmiterePermis());
        sofer.setDataExpirarePermis(newSoferDTO.getDataExpirarePermis());
        sofer.setAdresa(newSoferDTO.getAdresa());
        sofer.setTelefon(newSoferDTO.getTelefon());
        sofer.setEmail(newSoferDTO.getEmail());

        Sofer soferUpdated = soferRepo.save(sofer);
        soferDTOMapper.apply(soferUpdated);
    }

	


}
