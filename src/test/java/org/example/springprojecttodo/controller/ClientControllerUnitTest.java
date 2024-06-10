package org.example.springprojecttodo.controller;

import org.example.springprojecttodo.SpringProjectTodoApplication;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringProjectTodoApplication.class)
class ClientControllerUnitTest {

    private final MockMvc mockMvc;

    @Autowired
    ClientControllerUnitTest(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void createAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/clients"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Nested
    class GetByEmailTests {
        @Test
        void findByEmailEmpty() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/clients/email/null"))
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        }

        @Test
        void findByEmailShort() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/clients/email/qw"))
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        }

        @Test
        void findByEmailLong() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/clients/email/" + "q".repeat(21)))
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        }

        @Test
        void findByEmailWithoutDog() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/clients/email/qwerty.com"))
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        }

        @Test
        void findByEmail() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/clients/email/qwerty@gmail.com"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void findByEmailIncorrectTest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/clients/email/qw@erty@gmail.com"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Nested
    class DeleteClientTests {
        @Test
        void deleteClientEmpty() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/clients"))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }

        @Test
        void deleteClientIncorrectKey() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/clients?id=1"))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }

        @Test
        void deleteClient() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/clients?id=" + UUID.randomUUID()))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

}