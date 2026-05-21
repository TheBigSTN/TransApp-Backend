package com.app.trans.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.app.trans.repos.CompanyRepo;
import lombok.RequiredArgsConstructor;
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
    private final CompanyRepo companyRepo;

    @Transactional
    public LicentaDTO addLicenta(LicentaDTO licentaDTO, UUID companyId) {
        Licenta licenta = Licenta.builder()
                .tip(licentaDTO.getTip())
                .serie(licentaDTO.getSerie())
                .data_inceput(licentaDTO.getData_inceput())
                .data_final(licentaDTO.getData_final())
                .pret(licentaDTO.getPret())
                .company(companyRepo.getReferenceById(companyId))
                .build();

        if (licentaDTO.getMasinaId() == 0) {
            licenta.setMasina(masinaService.getMasinaByNumarInmatriculare(licentaDTO.getMasinaNumar(), companyId));
        }

        Licenta savedLicenta = licentaRepo.save(licenta);
        return licentaDTOMapper.apply(savedLicenta);
    }

    public List<LicentaDTO> getAllLicenta(UUID companyId) {
        return licentaRepo.findAllByCompanyId(companyId)
                .stream()
                .map(licentaDTOMapper)
                .collect(Collectors.toList());
    }

    public List<LicentaDTO> getLicentaPerioada(LocalDate startDate, LocalDate endDate, UUID companyId) {
        return licentaRepo.findAllByPeriodAndCompanyId(startDate, endDate, companyId)
                .stream()
                .map(licentaDTOMapper)
                .collect(Collectors.toList());
    }

    public LicentaDTO getLicentaById(long id, UUID companyId) {
        Licenta licenta = licentaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Licenta Not Found with ID: " + id));
        return licentaDTOMapper.apply(licenta);
    }

    @Transactional
    public void deleteLicenta(long id, UUID companyId) {
        Licenta licenta = licentaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Licenta Not Found with ID: " + id));
        licentaRepo.delete(licenta);
    }

    @Transactional
    public LicentaDTO updateLicenta(long id, LicentaDTO newLicenta, UUID companyId) {
        Licenta licenta = licentaRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Licenta Not Found with ID: " + id));

        licenta.setTip(newLicenta.getTip());
        licenta.setSerie(newLicenta.getSerie());
        licenta.setData_inceput(newLicenta.getData_inceput());
        licenta.setData_final(newLicenta.getData_final());
        licenta.setPret(newLicenta.getPret());

        if (licenta.getMasina() == null || licenta.getMasina().getId() != newLicenta.getMasinaId()) {
            Masina newMasina = masinaService.getMasinaEntityById(newLicenta.getMasinaId(), companyId);
            licenta.setMasina(newMasina);
        }

        Licenta savedLicenta = licentaRepo.save(licenta);
        return licentaDTOMapper.apply(savedLicenta);
    }
}
