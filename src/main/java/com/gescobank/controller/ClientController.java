package com.gescobank.controller;

import com.gescobank.dto.ClientDto;
import com.gescobank.entities.Client;
import com.gescobank.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")

public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/clients")
    void createClient(@RequestBody ClientDto clientDto){
        clientService.createNewClient(clientDto);

    }
    @GetMapping("/clients")
    List<Client> findAll(){
        return clientService.findAll();

    }
    @GetMapping("/clients/{id}")
    Optional<Client> findOne(@PathVariable("id") long id){
        return clientService.findOne(id);
    }
}
