package com.app.trans.controllers;

//import javax.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.trans.dtos.SoferDTO;
import com.app.trans.services.SoferService;

@RestController
@RequestMapping("/sofer")
public class SoferController {

    @Autowired
    SoferService soferService;

    @PostMapping("/add")
    public ResponseEntity<SoferDTO> addSofer(@RequestBody SoferDTO soferDTO) {
        SoferDTO newSoferDTO = soferService.addSofer(soferDTO);
        return ResponseEntity.ok(newSoferDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSoferById(@PathVariable Long id) {
        soferService.deleteSoferById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<SoferDTO> getSoferById(@PathVariable Long id) {
        SoferDTO soferDTO = soferService.getSoferById(id);
        return ResponseEntity.ok(soferDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SoferDTO>> getAllSoferDTO() {
        List<SoferDTO> soferDTOList = soferService.getAllSoferDTO();
        return ResponseEntity.ok(soferDTOList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SoferDTO> updateSoferById(@PathVariable Long id, @RequestBody SoferDTO soferDTO) {
        soferService.updateSoferById(id, soferDTO);
        return ResponseEntity.noContent().build();
    }
}
