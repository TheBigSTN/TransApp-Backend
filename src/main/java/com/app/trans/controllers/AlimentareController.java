package com.app.trans.controllers;

import com.app.trans.dtos.AlimentareDTO;
import com.app.trans.services.AlimentareService;
import com.app.trans.util.CurrentCompanyId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alimentare")
@RequiredArgsConstructor
public class AlimentareController {

    private final AlimentareService alimentareService;

    @PostMapping("/add")
    public ResponseEntity<AlimentareDTO> addAlimentare(
            @Valid @RequestBody AlimentareDTO alimentareDTO,
            @CurrentCompanyId UUID companyId) {
        AlimentareDTO newAlimentare = alimentareService.addAlimentare(alimentareDTO, companyId);
        return ResponseEntity.ok(newAlimentare);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAlimentareById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        alimentareService.deleteAlimentare(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<AlimentareDTO> getAlimentareById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        AlimentareDTO alimentareDTO = alimentareService.getAlimentareById(id, companyId);
        return ResponseEntity.ok(alimentareDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlimentareDTO>> getAllAlimentare(
            @CurrentCompanyId UUID companyId
    ) {
        List<AlimentareDTO> alimentareDTOList = alimentareService.getAllAlimentare(companyId);
        return ResponseEntity.ok(alimentareDTOList);
    }

    @GetMapping("/perioada")
    public List<AlimentareDTO> getAlimentarePerioada(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @CurrentCompanyId UUID companyId) {
        return alimentareService.getAlimentarePerioada(startDate, endDate, companyId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AlimentareDTO> updateAlimentareById(
            @PathVariable long id,
            @Valid @RequestBody AlimentareDTO alimentareDTO,
            @CurrentCompanyId UUID companyId) {
        AlimentareDTO updatedAlimentare = alimentareService.updateAlimentare(id, alimentareDTO, companyId);
        return ResponseEntity.ok(updatedAlimentare);
    }
}
