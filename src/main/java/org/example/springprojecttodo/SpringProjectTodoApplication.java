package org.example.springprojecttodo;

import org.example.springprojecttodo.service.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringProjectTodoApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(SpringProjectTodoApplication.class, args);

        final ClientService clientService = context.getBean(ClientService.class);
        clientService.createAdmin();
        System.out.println(clientService.findByEmail("admin@gmail.com"));
    }

}
