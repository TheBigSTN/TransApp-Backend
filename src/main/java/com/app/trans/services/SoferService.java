package com.app.trans.services;


import com.app.trans.dtos.SoferDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.SoferDTOMapper;
import com.app.trans.models.Sofer;
import com.app.trans.repos.CompanyRepo;
import com.app.trans.repos.SoferRepo;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SoferService {

    private final SoferRepo soferRepo;
    private final SoferDTOMapper soferDTOMapper;
    private final CompanyRepo companyRepo;

    @Transactional
    public SoferDTO addSofer(SoferDTO soferDTO, UUID companyId) {
        Sofer sofer = Sofer.builder()
                .nume(soferDTO.getNume())
                .prenume(soferDTO.getPrenume())
                .dataNastere(soferDTO.getDataNastere())
                .cnp(soferDTO.getCnp())
                .seriePermis(soferDTO.getSeriePermis())
                .dataEmiterePermis(soferDTO.getDataEmiterePermis())
                .dataExpirarePermis(soferDTO.getDataExpirarePermis())
                .adresa(soferDTO.getAdresa())
                .telefon(soferDTO.getTelefon())
                .email(soferDTO.getEmail())
                .company(companyRepo.getReferenceById(companyId))
                .build();

        Sofer savedSofer = soferRepo.save(sofer);
        return soferDTOMapper.apply(savedSofer);
    }

    public SoferDTO getSoferById(Long id, UUID companyId) {
        Sofer sofer = soferRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Sofer Not Found with ID: " + id));
        return soferDTOMapper.apply(sofer);
    }
    
    public Sofer getSoferEntityById(long id, UUID companyId) {
        return soferRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Sofer Not Found with ID: " + id));
    }

    public List<SoferDTO> getAllSofer(UUID companyId) {
        return soferRepo.findAllByCompanyId(companyId).stream()
                .map(soferDTOMapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSoferById(Long id, UUID companyId) {
        Sofer sofer = soferRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Sofer Not Found with ID: " + id));
        soferRepo.delete(sofer);
    }

    @Transactional
    public SoferDTO updateSoferById(Long id, SoferDTO newSoferDTO, UUID companyId) {
        Sofer sofer = soferRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Sofer Not Found with ID: " + id));
        
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
        return soferDTOMapper.apply(soferUpdated);
    }
}
