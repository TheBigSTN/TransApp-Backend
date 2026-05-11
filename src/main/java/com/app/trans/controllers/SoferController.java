package com.app.trans.controllers;

import com.app.trans.dtos.SoferDTO;
import com.app.trans.services.SoferService;
import com.app.trans.util.CurrentCompanyId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sofer")
@RequiredArgsConstructor
public class SoferController {

    private final SoferService soferService;

    @PostMapping("/add")
    public ResponseEntity<SoferDTO> addSofer(
            @Valid @RequestBody SoferDTO soferDTO,
            @CurrentCompanyId UUID companyId) {
        SoferDTO newSoferDTO = soferService.addSofer(soferDTO, companyId);
        return ResponseEntity.ok(newSoferDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSoferById(
            @PathVariable Long id,
            @CurrentCompanyId UUID companyId) {
        soferService.deleteSoferById(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<SoferDTO> getSoferById(
            @PathVariable Long id,
            @CurrentCompanyId UUID companyId) {
        SoferDTO soferDTO = soferService.getSoferById(id, companyId);
        return ResponseEntity.ok(soferDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SoferDTO>> getAllSofer(@CurrentCompanyId UUID companyId) {
        List<SoferDTO> soferDTOList = soferService.getAllSofer(companyId);
        return ResponseEntity.ok(soferDTOList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SoferDTO> updateSoferById(
            @PathVariable Long id,
            @Valid @RequestBody SoferDTO soferDTO,
            @CurrentCompanyId UUID companyId) {
        SoferDTO updatedSofer = soferService.updateSoferById(id, soferDTO, companyId);
        return ResponseEntity.ok(updatedSofer);
    }
}
