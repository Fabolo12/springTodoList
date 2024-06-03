package org.example.springprojecttodo.controller;

import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.service.ClientServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    // TODO 03.06.2024 sort
    // pagination
    // @ModelAttribute
    // @Valid

    private final ClientServiceI clientService;

    @Autowired
    ClientController(final ClientServiceI clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client createAdmin() {
        return clientService.createAdmin();
    }

    @GetMapping("/email/{email}")
    public Client findByEmail(@PathVariable final String email) {
        return clientService.findByEmail(email);
    }

    @GetMapping("/v2/email/{client-email}")
    public ResponseEntity<Client> findByClientEmail(@PathVariable("client-email") final String email) {
        return ResponseEntity.ok(clientService.findByEmail(email));
    }

    @DeleteMapping
    public void deleteClient(@RequestParam final UUID id) {
        clientService.deleteClient(id);
    }

    @PutMapping
    public void updateClient(@RequestBody final Client client) {
        clientService.updateClient(client);
    }

    @GetMapping("/my-user")
    public Client getMyUser(@CookieValue("user-id") final UUID id) {
        return clientService.getClient(id);
    }

    @GetMapping("/my-user2")
    public Client getMyUser(@RequestHeader("user-id") final String id) {
        return clientService.getClient(UUID.fromString(id));
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
