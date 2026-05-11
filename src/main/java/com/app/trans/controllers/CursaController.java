package com.app.trans.controllers;

import com.app.trans.dtos.CursaDTO;
import com.app.trans.services.CursaService;

import com.app.trans.util.CurrentCompanyId;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cursa")
public class CursaController {

    private final CursaService cursaService;
    
    @GetMapping("/perioada")
    public List<CursaDTO> getCursaPerioada(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @CurrentCompanyId UUID companyId) {
        return cursaService.getCursaPerioada(startDate, endDate, companyId);
    } 
    
    @PostMapping("/add")
    public ResponseEntity<CursaDTO> addCursa(
            @Valid @RequestBody CursaDTO cursaDTO,
            @CurrentCompanyId UUID companyId) {
        CursaDTO newCursa = cursaService.addCursa(cursaDTO, companyId);
        return ResponseEntity.ok(newCursa);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCursaById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        cursaService.deleteCursa(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<CursaDTO> getCursaById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        CursaDTO cursaDTO = cursaService.getCursaById(id, companyId);
        return ResponseEntity.ok(cursaDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CursaDTO>> getAllCursa(@CurrentCompanyId UUID companyId) {
        List<CursaDTO> cursaDTOList = cursaService.getAllCursa(companyId);
        return ResponseEntity.ok(cursaDTOList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CursaDTO> updateCursaById(
            @PathVariable long id,
            @Valid @RequestBody CursaDTO cursaDTO,
            @CurrentCompanyId UUID companyId) {
        CursaDTO updatedCursa = cursaService.updateCursa(id, cursaDTO, companyId);
        return ResponseEntity.ok(updatedCursa);
    }
    
    
   
}
