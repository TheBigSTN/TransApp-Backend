package com.app.trans.controllers;

import com.app.trans.dtos.MasinaDTO;
import com.app.trans.services.MasinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/masina")
@RequiredArgsConstructor
public class MasinaController {

    private final MasinaService masinaService;

    @PostMapping("/add")
    public ResponseEntity<MasinaDTO> addMasina(@Valid @RequestBody MasinaDTO masinaDTO) {
        MasinaDTO newMasina = masinaService.addMasina(masinaDTO);
        return ResponseEntity.ok(newMasina);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMasinaById(@PathVariable long id) {
        masinaService.deleteMasina(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<MasinaDTO> getMasinaById(@PathVariable long id) {
        MasinaDTO masinaDTO = masinaService.getMasinaById(id);
        return ResponseEntity.ok(masinaDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MasinaDTO>> getAllMasina() {
        List<MasinaDTO> masinaDTOList = masinaService.getAllMasina();
        return ResponseEntity.ok(masinaDTOList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MasinaDTO> updateMasinaById(@PathVariable long id, @Valid @RequestBody MasinaDTO masinaDTO) {
        MasinaDTO updatedMasina = masinaService.updateMasina(id, masinaDTO);
        return ResponseEntity.ok(updatedMasina);
    }
}