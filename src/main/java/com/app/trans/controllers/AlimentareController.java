package com.app.trans.controllers;

import com.app.trans.dtos.AlimentareDTO;
import com.app.trans.services.AlimentareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/alimentare")
@RequiredArgsConstructor
public class AlimentareController {

    private final AlimentareService alimentareService;

    @PostMapping("/add")
    public ResponseEntity<AlimentareDTO> addAlimentare(@Valid @RequestBody AlimentareDTO alimentareDTO) {
        AlimentareDTO newAlimentare = alimentareService.addAlimentare(alimentareDTO);
        return ResponseEntity.ok(newAlimentare);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAlimentareById(@PathVariable long id) {
        alimentareService.deleteAlimentare(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<AlimentareDTO> getAlimentareById(@PathVariable long id) {
        AlimentareDTO alimentareDTO = alimentareService.getAlimentareById(id);
        return ResponseEntity.ok(alimentareDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlimentareDTO>> getAllAlimentare() {
        List<AlimentareDTO> alimentareDTOList = alimentareService.getAllAlimentare();
        return ResponseEntity.ok(alimentareDTOList);
    }

    @GetMapping("/perioada")
    public List<AlimentareDTO> getAlimentarePerioada(@RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return alimentareService.getAlimentarePerioada(startDate, endDate);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AlimentareDTO> updateAlimentareById(@PathVariable long id,
            @Valid @RequestBody AlimentareDTO alimentareDTO) {
        AlimentareDTO updatedAlimentare = alimentareService.updateAlimentare(id, alimentareDTO);
        return ResponseEntity.ok(updatedAlimentare);
    }
}
