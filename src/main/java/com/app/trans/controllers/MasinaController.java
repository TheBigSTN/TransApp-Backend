package com.app.trans.controllers;

import com.app.trans.dtos.MasinaDTO;
import com.app.trans.services.MasinaService;
import com.app.trans.util.CurrentCompanyId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/masina")
@RequiredArgsConstructor
public class MasinaController {

    private final MasinaService masinaService;

    @PostMapping("/add")
    public ResponseEntity<MasinaDTO> addMasina(
            @Valid @RequestBody MasinaDTO masinaDTO,
            @CurrentCompanyId UUID companyId) {
        MasinaDTO newMasina = masinaService.addMasina(masinaDTO, companyId);
        return ResponseEntity.ok(newMasina);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMasinaById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        masinaService.deleteMasina(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<MasinaDTO> getMasinaById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        MasinaDTO masinaDTO = masinaService.getMasinaById(id, companyId);
        return ResponseEntity.ok(masinaDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MasinaDTO>> getAllMasina(@CurrentCompanyId UUID companyId) {
        List<MasinaDTO> masinaDTOList = masinaService.getAllMasina(companyId);
        return ResponseEntity.ok(masinaDTOList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MasinaDTO> updateMasinaById(
            @PathVariable long id,
            @Valid @RequestBody MasinaDTO masinaDTO,
            @CurrentCompanyId UUID companyId) {
        MasinaDTO updatedMasina = masinaService.updateMasina(id, masinaDTO, companyId);
        return ResponseEntity.ok(updatedMasina);
    }
}
