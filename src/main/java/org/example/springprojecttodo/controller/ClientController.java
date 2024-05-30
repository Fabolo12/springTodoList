package org.example.springprojecttodo.controller;

import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.service.ClientServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientServiceI clientService;

    @Autowired
    ClientController(final ClientServiceI clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getAll().toList();
    }

    @GetMapping("/count")
    public int clientsCount() {
        return clientService.countClients();
    }
}
