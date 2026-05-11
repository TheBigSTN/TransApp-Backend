package com.app.trans.services;

import com.app.trans.dtos.ClientDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.ClientDTOMapper;
import com.app.trans.models.Client;
import com.app.trans.repos.ClientRepo;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepo clientRepo;
    private final ClientDTOMapper clientDTOMapper;

    @Transactional
    public ClientDTO addClient(ClientDTO clientDTO) {
        Client client = new Client(clientDTO); // Use the apply method
        Client savedClient = clientRepo.save(client);
        return clientDTOMapper.apply(savedClient);
    }

    public List<ClientDTO> getAllClient() {
    	return clientRepo.findAll()
        		.stream()
        		.map(clientDTOMapper)
        		.collect(Collectors.toList());
    }

    public ClientDTO getClientById(long id) {
        return clientRepo.findById(id).map(clientDTOMapper).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));
    }
    
    public Client getClientEntityById(long id) {
        return clientRepo.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Client Not Found with ID: " + id));
	}
    
    @Transactional
    public void deleteClient(long id) {
        Client optionalClient = clientRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));

        clientRepo.delete(optionalClient);
    }
    
    @Transactional
    public ClientDTO updateClient(long id, ClientDTO newClient) {
        Client client = clientRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));
        client.setNume(newClient.getNume());
        client.setCui(newClient.getCui());
        client.setAdresaFacturare(newClient.getAdresaFacturare());
        client.setAdresaCorespondenta(newClient.getAdresaCorespondenta());
        client.setCod(newClient.getCod());
        client.setCont(newClient.getCont());
        client.setBanca(newClient.getBanca());
        client.setContact(newClient.getContact());
        client.setEmail(newClient.getEmail());
        client.setTelefon(newClient.getTelefon());
        Client clientSaved = clientRepo.save(client);
        return clientDTOMapper.apply(clientSaved);
    }

}
