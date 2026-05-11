package com.app.trans.services;

import com.app.trans.dtos.ClientDTO;
import com.app.trans.exceptions.ResourceNotFoundException;
import com.app.trans.mappers.ClientDTOMapper;
import com.app.trans.models.Client;
import com.app.trans.repos.ClientRepo;

import com.app.trans.repos.CompanyRepo;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepo clientRepo;
    private final CompanyRepo companyRepo;
    private final ClientDTOMapper clientDTOMapper;

    @Transactional
    public ClientDTO addClient(ClientDTO clientDTO, UUID companyId) {
        Client client = Client.builder()
                .nume(clientDTO.getNume())
                .cui(clientDTO.getCui())
                .adresaFacturare(clientDTO.getAdresaFacturare())
                .adresaCorespondenta(clientDTO.getAdresaCorespondenta())
                .cod(clientDTO.getCod())
                .cont(clientDTO.getCont())
                .banca(clientDTO.getBanca())
                .contact(clientDTO.getContact())
                .email(clientDTO.getEmail())
                .telefon(clientDTO.getTelefon())
                .company(companyRepo.getReferenceById(companyId))
                .build();
        Client savedClient = clientRepo.save(client);
        return clientDTOMapper.apply(savedClient);
    }

    public List<ClientDTO> getAllClient(UUID companyId) {
    	return clientRepo.findAllByCompanyId(companyId)
        		.stream()
        		.map(clientDTOMapper)
        		.collect(Collectors.toList());
    }

    public ClientDTO getClientById(long id, UUID companyId) {
        return clientRepo.findByIdAndCompanyId(id, companyId).map(clientDTOMapper).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));
    }
    
    public Client getClientEntityById(long id) {
        return clientRepo.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Client Not Found with ID: " + id));
	}
    
    @Transactional
    public void deleteClient(long id, UUID companyId) {
        Client optionalClient = clientRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
                new ResourceNotFoundException("Client Not Found with ID: " + id));

        clientRepo.delete(optionalClient);
    }
    
    @Transactional
    public ClientDTO updateClient(long id, ClientDTO newClient, UUID companyId) {
        Client client = clientRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() ->
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
