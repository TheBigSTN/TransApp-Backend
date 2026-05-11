package com.app.trans.controllers;

import com.app.trans.dtos.LicentaDTO;
import com.app.trans.services.LicentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/licenta")
@RequiredArgsConstructor
public class LicentaController {

    private final LicentaService licentaService;

    @PostMapping("/add")
    public ResponseEntity<LicentaDTO> addLicenta(@Valid @RequestBody LicentaDTO licentaDTO) {
        LicentaDTO newLicenta = licentaService.addLicenta(licentaDTO);
        return ResponseEntity.ok(newLicenta);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLicentaById(@PathVariable long id) {
        licentaService.deleteLicenta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<LicentaDTO> getLicentaById(@PathVariable long id) {
        LicentaDTO licentaDTO = licentaService.getLicentaById(id);
        return ResponseEntity.ok(licentaDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LicentaDTO>> getAllLicenta() {
        List<LicentaDTO> licentaDTOList = licentaService.getAllLicenta();
        return ResponseEntity.ok(licentaDTOList);
    }

    @GetMapping("/perioada")
    public List<LicentaDTO> getLicentaPerioada(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return licentaService.getLicentaPerioada(startDate, endDate);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LicentaDTO> updateLicentaById(@PathVariable long id,
            @Valid @RequestBody LicentaDTO licentaDTO) {
        LicentaDTO updatedLicenta = licentaService.updateLicenta(id, licentaDTO);
        return ResponseEntity.ok(updatedLicenta);
    }
}
