package com.app.trans.services;

import com.app.trans.dtos.AlimentareDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.AlimentareDTOMapper;
import com.app.trans.models.Alimentare;
import com.app.trans.models.Masina;
import com.app.trans.repos.AlimentareRepo;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlimentareService {

    private final AlimentareRepo alimentareRepo;
    private final AlimentareDTOMapper alimentareDTOMapper;
    private final MasinaService masinaService;

    @Transactional
    public AlimentareDTO addAlimentare(AlimentareDTO alimentareDTO) {
        Alimentare alimentare = new Alimentare(alimentareDTO);
        Alimentare savedAlimentare = alimentareRepo.save(alimentare);
        return alimentareDTOMapper.apply(savedAlimentare);
    }

    public List<AlimentareDTO> getAllAlimentare() {
        return alimentareRepo.findAll()
                .stream()
                .map(alimentareDTOMapper)
                .collect(Collectors.toList());
    }

    public List<AlimentareDTO> getAlimentarePerioada(LocalDate startDate, LocalDate endDate) {
        return alimentareRepo.findAllByPeriod(startDate, endDate)
                .stream()
                .map(alimentareDTOMapper)
                .collect(Collectors.toList());
    }

    public AlimentareDTO getAlimentareById(long id) {
        Alimentare alimentare = alimentareRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));
        return alimentareDTOMapper.apply(alimentare);
    }

    @Transactional
    public void deleteAlimentare(long id) {
        Alimentare alimentare = alimentareRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));

        alimentareRepo.delete(alimentare);
    }

    @Transactional
    public AlimentareDTO updateAlimentare(long id, AlimentareDTO newAlimentare) {
        Alimentare alimentare = alimentareRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));

        alimentare.setData_alimentare(newAlimentare.getDataAlimentare());
        alimentare.setLitri(newAlimentare.getLitri());
        alimentare.setPret_unitar(newAlimentare.getPretUnitar());
        alimentare.setTip(newAlimentare.getTip());

        if (alimentare.getMasina().getId() != newAlimentare.getMasinaId()) {
            Masina newMasina = masinaService.getMasinaEntityById(newAlimentare.getMasinaId());
            alimentare.setMasina(newMasina);
        }

        Alimentare savedAlimentare = alimentareRepo.save(alimentare);
        return alimentareDTOMapper.apply(savedAlimentare);
    }
}
