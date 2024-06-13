package org.example.springprojecttodo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springprojecttodo.SpringProjectTodoApplication;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.service.ClientServiceI;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringProjectTodoApplication.class)
class ClientControllerUnitTest {

    private final MockMvc mockMvc;

    private final ObjectMapper mapper;

    @MockBean
    private ClientServiceI clientService;

    @Autowired
    ClientControllerUnitTest(final MockMvc mockMvc, final ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
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

    @Nested
    class PutClientTests {
        @Test
        void updateClientWithoutBody() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.put("/clients"))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }

        @Test
        void updateClientBodyWithoutId() throws Exception {
            mockMvc.perform(prepareRequestBuilder(Client.builder().build()))
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                    .andExpect(MockMvcResultMatchers.content().string("id: must not be null"));
        }

        @Test
        void updateClientBodyWithShortName() throws Exception {
            mockMvc.perform(prepareRequestBuilder(Client.builder().id(UUID.randomUUID()).name("q").build()))
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                    .andExpect(MockMvcResultMatchers.content().string("name: size must be between 3 and 20"));
        }

        @Test
        void updateClientBodyWithLongName() throws Exception {
            mockMvc.perform(prepareRequestBuilder(
                            Client.builder().id(UUID.randomUUID()).name("q".repeat(21)).build())
                    )
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                    .andExpect(MockMvcResultMatchers.content().string("name: size must be between 3 and 20"));
        }

        @Test
        void updateClient() throws Exception {
            mockMvc.perform(prepareRequestBuilder(
                            Client.builder()
                                    .id(UUID.randomUUID())
                                    .name("qwerty")
                                    .build())
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        private RequestBuilder prepareRequestBuilder(final Client client) throws JsonProcessingException {
            return MockMvcRequestBuilders.put("/clients")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(client));
        }
    }

}