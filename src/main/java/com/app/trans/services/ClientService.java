package com.app.trans.services;

import com.app.trans.dtos.ClientDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.ClientDTOMapper;
import com.app.trans.models.Client;
import com.app.trans.repos.ClientRepo;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Optional<ClientDTO> optionalClient = clientRepo.findById(id).map(clientDTOMapper);
        if (!optionalClient.isPresent()) {
            throw new ResourceNotFoundException("Client Not Found with ID: " + id);
        }
        return optionalClient.get();
    }
    
    public Client getClientEntityById(long id) {
    	Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent()) {
            throw new ResourceNotFoundException("Client Not Found with ID: " + id);
        }
        return optionalClient.get();
	}
    
    @Transactional
    public void deleteClient(long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent()) {
            throw new ResourceNotFoundException("Client Not Found with ID: " + id);
        }
        Client client = optionalClient.get();
        clientRepo.delete(client);
    }
    
    @Transactional
    public ClientDTO updateClient(long id, ClientDTO newClient) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent()) {
            throw new ResourceNotFoundException("Client Not Found with ID: " + id);
        }
        Client client = optionalClient.get();
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
