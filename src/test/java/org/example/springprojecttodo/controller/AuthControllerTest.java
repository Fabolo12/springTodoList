package org.example.springprojecttodo.controller;

import org.example.springprojecttodo.bean.LogInRequestBean;
import org.example.springprojecttodo.bean.SignUpRequestBean;
import org.example.springprojecttodo.service.ClientServiceJpa;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AuthControllerTest extends AbstractControllerTest {

    private static final String URI = "/auth";

    @Autowired
    private ClientServiceJpa clientService;

    @Nested
    class SingUpTests {

        private static final String URI_SIGN_UP = URI + "/sign-up";

        @Test
        void signUpEmptyBody() throws Exception {
            mockMvc.perform(prepareRequestBuilderPut(URI_SIGN_UP, null))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }

        @Test
        void signUpAllParamsWrong() throws Exception {
            mockMvc.perform(prepareRequestBuilderPost(URI_SIGN_UP, new SignUpRequestBean(null, null, null, false)))
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                    .andExpect(MockMvcResultMatchers.content()
                            .string("acceptTerms: must be true, "
                                    + "email: must not be null, "
                                    + "name: must not be null, "
                                    + "password: must not be null"
                            )
                    );
        }

        @Test
        void signUp() throws Exception {
            mockMvc.perform(prepareRequestBuilderPost(
                            URI_SIGN_UP,
                            new SignUpRequestBean("Test", "Test", "Test", true)
                    ))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Nested
    class LogInTests {

        private static final String URI_LOG_IN = URI + "/log-in";

        @Test
        void logInEmptyBody() throws Exception {
            mockMvc.perform(prepareRequestBuilderPut(URI_LOG_IN, null))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }

        @Test
        void logInAllParamsWrong() throws Exception {
            mockMvc.perform(prepareRequestBuilderPost(URI_LOG_IN, new LogInRequestBean(null, null)))
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                    .andExpect(MockMvcResultMatchers.content()
                            .string("email: must not be null, password: must not be null")
                    );
        }

        @Test
        void logInWithoutUser() throws Exception {
            mockMvc.perform(prepareRequestBuilderPost(
                            URI_LOG_IN,
                            new LogInRequestBean("Test", "Test")
                    ))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(result -> assertEquals("false", result.getResponse().getContentAsString()));
        }

        @Test
        void logInWithUser() throws Exception {
            clientService.createUser(new SignUpRequestBean("Test", "Test", "Test", true));

            mockMvc.perform(prepareRequestBuilderPost(
                            URI_LOG_IN,
                            new LogInRequestBean("Test", "Test")
                    ))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(result -> assertEquals("true", result.getResponse().getContentAsString()));
        }
    }
}