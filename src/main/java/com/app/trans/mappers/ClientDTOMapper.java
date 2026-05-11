package com.app.trans.mappers;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.app.trans.dtos.ClientDTO;
import com.app.trans.models.Client;

@Service
public class ClientDTOMapper implements Function<Client, ClientDTO> {

    @Override
    public ClientDTO apply(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getNume(),
                client.getCui(),
                client.getAdresaFacturare(),
                client.getAdresaCorespondenta(),
                client.getCod(),
                client.getCont(),
                client.getBanca(),
                client.getContact(),
                client.getEmail(),
                client.getTelefon()
        );
        // Set associated entity IDs if needed
        // client.getCurse().stream().map(Cursa::getId).collect(Collectors.toList()),
        // client.getSomeOtherList().stream().map(OtherEntity::getId).collect(Collectors.toList())
    }
}
