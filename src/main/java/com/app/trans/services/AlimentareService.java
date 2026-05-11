package com.app.trans.services;

import com.app.trans.dtos.AlimentareDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.AlimentareDTOMapper;
import com.app.trans.models.Alimentare;
import com.app.trans.models.Company;
import com.app.trans.models.Masina;
import com.app.trans.repos.AlimentareRepo;

import com.app.trans.repos.CompanyRepo;
import com.app.trans.repos.MasinaRepo;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlimentareService {

    private final AlimentareRepo alimentareRepo;
    private final AlimentareDTOMapper alimentareDTOMapper;
    private final MasinaService masinaService;
    private final MasinaRepo masinaRepo;
    private final CompanyRepo companyRepo;

    @Transactional
    public AlimentareDTO addAlimentare(AlimentareDTO dto, UUID companyId) {
        Masina masina = masinaRepo.getReferenceById(dto.getMasinaId());
        Company company = companyRepo.getReferenceById(companyId);
        Alimentare alimentare = Alimentare.builder()
                .data_alimentare(dto.getDataAlimentare())
                .litri(dto.getLitri())
                .pret_unitar(dto.getPretUnitar())
                .tip(dto.getTip())
                .masina(masina)
                .company(company)
                .build();
        Alimentare savedAlimentare = alimentareRepo.save(alimentare);
        return alimentareDTOMapper.apply(savedAlimentare);
    }

    public List<AlimentareDTO> getAllAlimentare(UUID companyId) {
        return alimentareRepo.findAllByCompanyId(companyId)
                .stream()
                .map(alimentareDTOMapper)
                .collect(Collectors.toList());
    }

    public List<AlimentareDTO> getAlimentarePerioada(LocalDate startDate, LocalDate endDate, UUID companyId) {
        return alimentareRepo.findAllByPeriod(startDate, endDate, companyId)
                .stream()
                .map(alimentareDTOMapper)
                .collect(Collectors.toList());
    }

    public AlimentareDTO getAlimentareById(long id, UUID companyId) {
        Alimentare alimentare = alimentareRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));

        return alimentareDTOMapper.apply(alimentare);
    }

    @Transactional
    public void deleteAlimentare(long id, UUID companyId) {
        Alimentare alimentare = alimentareRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));

        alimentareRepo.delete(alimentare);
    }

    @Transactional
    public AlimentareDTO updateAlimentare(long id, AlimentareDTO newAlimentare, UUID companyId) {
        Alimentare alimentare = alimentareRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));

        alimentare.setData_alimentare(newAlimentare.getDataAlimentare());
        alimentare.setLitri(newAlimentare.getLitri());
        alimentare.setPret_unitar(newAlimentare.getPretUnitar());
        alimentare.setTip(newAlimentare.getTip());

        if (alimentare.getMasina().getId() != newAlimentare.getMasinaId()) {
            Masina newMasina = masinaService.getMasinaEntityById(newAlimentare.getMasinaId(),companyId);
            alimentare.setMasina(newMasina);
        }

        Alimentare savedAlimentare = alimentareRepo.save(alimentare);
        return alimentareDTOMapper.apply(savedAlimentare);
    }
}
