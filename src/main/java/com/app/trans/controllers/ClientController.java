package com.app.trans.controllers;

import com.app.trans.dtos.ClientDTO;
import com.app.trans.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO newClient = clientService.addClient(clientDTO);
        return ResponseEntity.ok(newClient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable long id) {
        ClientDTO clientDTO = clientService.getClientById(id);
        return ResponseEntity.ok(clientDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientDTO>> getAllClient() {
        List<ClientDTO> clientDTOList = clientService.getAllClient();
        return ResponseEntity.ok(clientDTOList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDTO> updateClientById(@PathVariable long id, @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }
}
