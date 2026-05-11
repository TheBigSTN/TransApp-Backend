package com.app.trans.controllers;

import java.util.List;
import java.util.UUID;

import com.app.trans.util.CurrentCompanyId;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AnexaDTO> validateAnexa(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId
    ) {
        try {
            log.info("in the controller <{}>", id);
            Anexa anexa = anexaService.validareAnexa(id, companyId);
            return ResponseEntity.ok(anexaDTOMapper.apply(anexa));
        } catch (Exception e) {
            log.warn("Validating Anexa failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @GetMapping("/view/{id}")
    public ResponseEntity<AnexaDTO> getAnexaById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        log.error("sunt in view anexa");
        AnexaDTO anexaDTO = anexaService.getAnexaByIdAndCompanyId(id, companyId);
        return ResponseEntity.ok(anexaDTO);
    }

    @GetMapping("/generate/{id}")
    public ResponseEntity<InputStreamResource> generateAnexa(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        log.info("I am here in generate anexa and id= {}", id);
        // log.info("pdf-ul dupa generare" + pdf.getInputStream().toString() );
        // String pdfContent = readPdfContent(pdf.getInputStream());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=anexa.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(anexaService.generateAnexa(id, companyId));
    }

    // private String readPdfContent(InputStream inputStream) throws IOException {
    // try (PDDocument document = PDDocument.load(inputStream)) {
    // PDFTextStripper pdfStripper = new PDFTextStripper();
    // return pdfStripper.getText(document);
    // }
    // }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnexaById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        anexaService.deleteAnexa(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnexaDTO>> getAllAnexa(
            @CurrentCompanyId UUID companyId
    ) {
        List<AnexaDTO> anexaDTOList = anexaService.getAllAnexa(companyId);
        return ResponseEntity.ok(anexaDTOList);
    }

    @PostMapping("/add")
    public ResponseEntity<AnexaDTO> addAnexa(
            @RequestBody List<Long> cursaIds,
            @CurrentCompanyId UUID companyId) {
        AnexaDTO newAnexa = anexaService.addAnexa(cursaIds, companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnexa);
    }
}
