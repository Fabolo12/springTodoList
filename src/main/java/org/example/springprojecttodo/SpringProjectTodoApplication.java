package org.example.springprojecttodo;

import org.example.springprojecttodo.analytic.CronService;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.ClientStatus;
import org.example.springprojecttodo.service.ClientServiceI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@EnableScheduling
@SpringBootApplication
public class SpringProjectTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectTodoApplication.class, args);
    }

    private static void standardMethods() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
                "http://localhost:8080/clients",
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        ResponseEntity<List<Client>> response = restTemplate.exchange(
                "http://localhost:8080/clients",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        System.out.println("Data from standard get:");
        response.getBody().stream().forEach(System.out::println);
    }

    private static void reactiveMethods() {
        WebClient client = WebClient.create("http://localhost:8080");

        client.post().uri("/clients").retrieve().bodyToMono(Client.class)
                .subscribe(c -> {
                    System.out.println("Data from reactive create: " + c);
                    client.get().uri("/clients/reactive").retrieve().bodyToFlux(Client.class)
                            .subscribe(c1 -> System.out.println("Data from reactive get after create: " + c1));
                });


        client.get().uri("/clients/reactive").retrieve().bodyToFlux(Client.class)
                .subscribe(c -> System.out.println("Data from reactive get: " + c));
    }

    private static void internalUse(final ConfigurableApplicationContext context) {
        final ClientServiceI clientService = context.getBean(ClientServiceI.class);
        final Client admin = clientService.createAdmin();

        System.out.println("Find by email: " + clientService.findByEmail("admin@gmail.com"));

        System.out.println("Count of clients: " + clientService.countClients());

        clientService.deleteClient(admin.getId());
        System.out.println("Find after deleting: " + clientService.findByEmail("admin@gmail.com"));

        admin.setStatus(ClientStatus.ACTIVE);
        clientService.updateClient(admin);

        System.out.println("Get by id: " + clientService.getClient(admin.getId()));
        System.out.println("Get all: " + clientService.getAll().count());
    }

}
