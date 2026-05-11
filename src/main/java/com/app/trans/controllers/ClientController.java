package com.app.trans.controllers;

import com.app.trans.dtos.ClientDTO;
import com.app.trans.services.ClientService;
import com.app.trans.util.CurrentCompanyId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<ClientDTO> addClient(
            @RequestBody ClientDTO clientDTO,
            @CurrentCompanyId UUID companyId) {
        ClientDTO newClient = clientService.addClient(clientDTO, companyId);
        return ResponseEntity.ok(newClient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        clientService.deleteClient(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ClientDTO> getClientById(
            @PathVariable long id,
            @CurrentCompanyId UUID companyId) {
        ClientDTO clientDTO = clientService.getClientById(id, companyId);
        return ResponseEntity.ok(clientDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientDTO>> getAllClient(
            @CurrentCompanyId UUID companyId
    ) {
        List<ClientDTO> clientDTOList = clientService.getAllClient(companyId);
        return ResponseEntity.ok(clientDTOList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDTO> updateClientById(
            @PathVariable long id,
            @RequestBody ClientDTO clientDTO,
            @CurrentCompanyId UUID companyId) {
        ClientDTO updatedClient = clientService.updateClient(id, clientDTO, companyId);
        return ResponseEntity.ok(updatedClient);
    }
}
