package com.app.trans.controllers;

import com.app.trans.dtos.CursaDTO;
import com.app.trans.services.CursaService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cursa")
public class CursaController {

    private final CursaService cursaService;
    
    @GetMapping("/perioada")
    public List<CursaDTO> getCursaPerioada(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return cursaService.getCursaPerioada(startDate, endDate);
    } 
    
    @PostMapping("/add")
    public ResponseEntity<CursaDTO> addCursa(@Valid @RequestBody CursaDTO cursaDTO) {
        CursaDTO newCursa = cursaService.addCursa(cursaDTO);
        return ResponseEntity.ok(newCursa);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCursaById(@PathVariable long id) {
        cursaService.deleteCursa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<CursaDTO> getCursaById(@PathVariable long id) {
        CursaDTO cursaDTO = cursaService.getCursaById(id);
        return ResponseEntity.ok(cursaDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CursaDTO>> getAllCursa() {
        List<CursaDTO> cursaDTOList = cursaService.getAllCursa();
        return ResponseEntity.ok(cursaDTOList);
    }
    
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<CursaDTO> updateCursaById(@PathVariable long id, @Valid @RequestBody CursaDTO cursaDTO) {
        CursaDTO updatedCursa = cursaService.updateCursa(id, cursaDTO);
        return ResponseEntity.ok(updatedCursa);
    }
    
    
   
}
