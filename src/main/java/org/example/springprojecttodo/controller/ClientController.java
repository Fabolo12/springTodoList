package org.example.springprojecttodo.controller;

import jakarta.validation.Valid;
import org.example.springprojecttodo.dto.ClientsFilter;
import org.example.springprojecttodo.exeption.IllegalUserInputException;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.service.ClientServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

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
    public void updateClient(@Valid @RequestBody final Client client, final BindingResult result) {
        if (result.hasErrors()) {
            final String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new IllegalUserInputException(errorMessage);
        }
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
    public List<Client> getClients(
            @RequestParam(defaultValue = "email") final String sortField,
            @RequestParam(defaultValue = "ASC") final Sort.Direction direction,
            @RequestParam(required = false) final Integer page,
            @RequestParam(required = false) final Integer size
    ) {
        final Sort sort = Sort.by(direction, sortField);

        if (page != null && size != null) {
            final Pageable pageable = PageRequest.of(page, size, sort);

            Pageable.ofSize(size)
                    .withPage(page);
            return clientService.getAll(pageable).toList();

        }

        return clientService.getAll(sort).toList();
    }

    @GetMapping("/filtered")
    public List<Client> getClients(
            @ModelAttribute final ClientsFilter filter
    ) {
        System.out.println(filter);
        return clientService.getAll().toList();
    }

    @GetMapping("/count")
    public int clientsCount() {
        return clientService.countClients();
    }
}
