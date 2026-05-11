package com.app.trans.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.trans.dtos.LicentaDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.LicentaDTOMapper;
import com.app.trans.models.Licenta;
import com.app.trans.models.Masina;
import com.app.trans.repos.LicentaRepo;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LicentaService {

    private final LicentaRepo licentaRepo;
    private final LicentaDTOMapper licentaDTOMapper;
    private final MasinaService masinaService;

    @Transactional
    public LicentaDTO addLicenta(LicentaDTO licentaDTO) {
        Licenta licenta = new Licenta(licentaDTO);
        Licenta savedLicenta = licentaRepo.save(licenta);
        return licentaDTOMapper.apply(savedLicenta);
    }

    public List<LicentaDTO> getAllLicenta() {
        return licentaRepo.findAll()
                .stream()
                .map(licentaDTOMapper)
                .collect(Collectors.toList());
    }

    public List<LicentaDTO> getLicentaPerioada(LocalDate startDate, LocalDate endDate) {
        return licentaRepo.findAllByPeriod(startDate, endDate)
                .stream()
                .map(licentaDTOMapper)
                .collect(Collectors.toList());
    }

    public LicentaDTO getLicentaById(long id) {
        Optional<Licenta> optionalLicenta = licentaRepo.findById(id);
        if (!optionalLicenta.isPresent()) {
            throw new ResourceNotFoundException("Licenta Not Found with ID: " + id);
        }
        return licentaDTOMapper.apply(optionalLicenta.get());
    }

    @Transactional
    public void deleteLicenta(long id) {
        Optional<Licenta> optionalLicenta = licentaRepo.findById(id);
        if (!optionalLicenta.isPresent()) {
            throw new ResourceNotFoundException("Licenta Not Found with ID: " + id);
        }
        Licenta licenta = optionalLicenta.get();
        licentaRepo.delete(licenta);
    }

    @Transactional
    public LicentaDTO updateLicenta(long id, LicentaDTO newLicenta) {
        Optional<Licenta> optionalLicenta = licentaRepo.findById(id);
        if (!optionalLicenta.isPresent()) {
            throw new ResourceNotFoundException("Licenta Not Found with ID: " + id);
        }

        Licenta licenta = optionalLicenta.get();
        licenta.setTip(newLicenta.getTip());
        licenta.setSerie(newLicenta.getSerie());
        licenta.setData_inceput(newLicenta.getData_inceput());
        licenta.setData_final(newLicenta.getData_final());

        if (licenta.getMasina().getId() != newLicenta.getMasinaId()) {
            Masina newMasina = masinaService.getMasinaEntityById(newLicenta.getMasinaId());
            licenta.setMasina(newMasina);
        }

        Licenta savedLicenta = licentaRepo.save(licenta);
        return licentaDTOMapper.apply(savedLicenta);
    }
}
