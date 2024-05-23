package org.example.springprojecttodo;

import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.ClientStatus;
import org.example.springprojecttodo.service.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringProjectTodoApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(SpringProjectTodoApplication.class, args);

        final ClientService clientService = context.getBean(ClientService.class);
        final Client admin = clientService.createAdmin();

        System.out.println("Find by email: " + clientService.findByEmail("admin@gmail.com"));

        System.out.println("Count of clients: " + clientService.countClients());

        clientService.deleteClient(admin.getId());

        admin.setStatus(ClientStatus.ACTIVE);
        clientService.updateClient(admin);

        System.out.println("Get by id: " + clientService.getClient(admin.getId()));
        System.out.println("Get all: " + clientService.getAll().count());
    }

}
