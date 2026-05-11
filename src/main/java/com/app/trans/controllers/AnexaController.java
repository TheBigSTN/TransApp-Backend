package com.app.trans.controllers;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.trans.dtos.AnexaDTO;
import com.app.trans.mappers.AnexaDTOMapper;
import com.app.trans.models.Anexa;
import com.app.trans.services.AnexaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/anexa")
@RequiredArgsConstructor
@Slf4j
public class AnexaController {

    private final AnexaService anexaService;
    private final AnexaDTOMapper anexaDTOMapper;

    @GetMapping("/validate/{id}")
    public ResponseEntity<AnexaDTO> validateAnexa(@PathVariable long id) {
        try {
            log.info("in the controller <{}>", id);
            Anexa anexa = anexaService.validareAnexa(id);
            return ResponseEntity.ok(anexaDTOMapper.apply(anexa));
        } catch (Exception e) {
            log.warn("Validating Anexa failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @GetMapping("/view/{id}")
    public ResponseEntity<AnexaDTO> getAnexaById(@PathVariable long id) {
        log.error("sunt in view anexa");
        AnexaDTO anexaDTO = anexaService.getAnexaById(id);
        return ResponseEntity.ok(anexaDTO);
    }

    @GetMapping("/generate/{id}")
    public ResponseEntity<InputStreamResource> generateAnexa(@PathVariable long id) {
        log.info("I am here in generate anexa and id= {}", id);
        // log.info("pdf-ul dupa generare" + pdf.getInputStream().toString() );
        // String pdfContent = readPdfContent(pdf.getInputStream());
        return ResponseEntity.ok(anexaService.generateAnexa(id));
    }

    // private String readPdfContent(InputStream inputStream) throws IOException {
    // try (PDDocument document = PDDocument.load(inputStream)) {
    // PDFTextStripper pdfStripper = new PDFTextStripper();
    // return pdfStripper.getText(document);
    // }
    // }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnexaById(@PathVariable long id) {
        anexaService.deleteAnexa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnexaDTO>> getAllAnexa() {
        List<AnexaDTO> anexaDTOList = anexaService.getAllAnexa();
        return ResponseEntity.ok(anexaDTOList);
    }
}
