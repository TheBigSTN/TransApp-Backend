package com.app.trans.services;

import com.app.trans.dtos.MasinaDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.MasinaDTOMapper;
import com.app.trans.models.Masina;
import com.app.trans.repos.CompanyRepo;
import com.app.trans.repos.MasinaRepo;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MasinaService {

    private final MasinaRepo masinaRepo;
    private final MasinaDTOMapper masinaDTOMapper;
    private final CompanyRepo companyRepo;

    @Transactional
    public MasinaDTO addMasina(MasinaDTO masinaDTO, UUID companyId) {
        Masina masina = Masina.builder()
                .numar(masinaDTO.getNumar())
                .serie(masinaDTO.getSerie())
                .capacitateTransport(masinaDTO.getCapacitateTransport())
                .capacitateCombustibil(masinaDTO.getCapacitateCombustibil())
                .tipauto(masinaDTO.getTipauto())
                .status(masinaDTO.getStatus())
                .company(companyRepo.getReferenceById(companyId))
                .build();

        Masina savedMasina = masinaRepo.save(masina);
        return masinaDTOMapper.apply(savedMasina);
    }
    
    public List<MasinaDTO> getAllMasina(UUID companyId) {
        return masinaRepo.findAllByCompanyId(companyId)
        		.stream()
        		.map(masinaDTOMapper)
        		.collect(Collectors.toList());
    }

    public MasinaDTO getMasinaById(long id, UUID companyId) {
        Masina masina = masinaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Masina Not Found with ID: " + id));

        return masinaDTOMapper.apply(masina);
    }
    
    public Masina getMasinaEntityById(long id, UUID companyId) {
        return masinaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
            new ResourceNotFoundException("Masina Not Found with ID: " + id));
    }

    @Transactional
    public void deleteMasina(long id, UUID companyId) {
        Masina masina = masinaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Masina Not Found with ID: " + id));

        masinaRepo.delete(masina);
    }

    @Transactional
    public MasinaDTO updateMasina(long id, MasinaDTO newMasina, UUID companyId) {
        Masina masina = masinaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Masina Not Found with ID: " + id));

        masina.setNumar(newMasina.getNumar());
        masina.setSerie(newMasina.getSerie());
        masina.setCapacitateTransport(newMasina.getCapacitateTransport());
        masina.setCapacitateCombustibil(newMasina.getCapacitateCombustibil());
        masina.setTipauto(newMasina.getTipauto());
        masina.setStatus(newMasina.getStatus());

        Masina masinaSaved = masinaRepo.save(masina);
        return masinaDTOMapper.apply(masinaSaved);
    }

}
