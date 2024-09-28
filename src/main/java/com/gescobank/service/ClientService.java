package com.gescobank.service;

import com.gescobank.dto.ClientDto;
import com.gescobank.entities.Client;
import org.springframework.stereotype.Service;

import java.util.List;

import com.gescobank.dto.ClientDto;
import com.gescobank.entities.Client;
import com.gescobank.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public void createNewClient(ClientDto clientDto){
        Client client = new Client();
        client.setNom(clientDto.getNom());
        client.setBirthday(clientDto.getBirthday());
        client.setEmail(clientDto.getEmail());
        client.setPrenom(clientDto.getPrenom());
        client.setTelephone(clientDto.getTelephone());

        clientRepository.save(client);
    }
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Optional<Client> findOne(long id){
        return Optional.of(clientRepository.getReferenceById(id));
    }
}

