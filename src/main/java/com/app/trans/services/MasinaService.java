package com.app.trans.services;

import com.app.trans.dtos.MasinaDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.MasinaDTOMapper;
import com.app.trans.models.Masina;
import com.app.trans.repos.MasinaRepo;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MasinaService {

    private final MasinaRepo masinaRepo;
    private final MasinaDTOMapper masinaDTOMapper;

    @Transactional
    public MasinaDTO addMasina(MasinaDTO masinaDTO) {
        Masina masina = new Masina(masinaDTO);
        Masina savedMasina = masinaRepo.save(masina);
        return masinaDTOMapper.apply(savedMasina);
    }
    
    public List<MasinaDTO> getAllMasina() {
        return masinaRepo.findAll()
        		.stream()
        		.map(masinaDTOMapper)
        		.collect(Collectors.toList());
    }

    public MasinaDTO getMasinaById(long id) {
        Optional<MasinaDTO> optionalMasina = masinaRepo.findById(id).map(masinaDTOMapper);
        if (!optionalMasina.isPresent()) {
            throw new ResourceNotFoundException("Masina Not Found with ID: " + id);
        }
        return optionalMasina.get();
    }
    
    public Masina getMasinaEntityById(long id) {
    	Optional<Masina> optionalMasina = masinaRepo.findById(id);
        if (!optionalMasina.isPresent()) {
            throw new ResourceNotFoundException("Masina Not Found with ID: " + id);
        }
        return optionalMasina.get();
    }

    @Transactional
    public void deleteMasina(long id) {
        Optional<Masina> optionalMasina = masinaRepo.findById(id);
        if (!optionalMasina.isPresent()) {
            throw new ResourceNotFoundException("Masina Not Found with ID: " + id);
        }
        Masina masina = optionalMasina.get();
        masinaRepo.delete(masina);
    }

    @Transactional
    public MasinaDTO updateMasina(long id, MasinaDTO newMasina) {
        Optional<Masina> optionalMasina = masinaRepo.findById(id);
        if (!optionalMasina.isPresent()) {
            throw new ResourceNotFoundException("Masina Not Found with ID: " + id);
        }
        
        Masina masina = optionalMasina.get();
        masina.setNumar(newMasina.getNumar());
        masina.setSerie(newMasina.getSerie());
        masina.setCapacitateTransport(newMasina.getCapacitateTransport());
        masina.setCapacitateCombustibil(newMasina.getCapacitateCombustibil());
        masina.setStatus(newMasina.getStatus());
        Masina masinaSaved = masinaRepo.save(masina);
        return masinaDTOMapper.apply(masinaSaved);
    }

}
