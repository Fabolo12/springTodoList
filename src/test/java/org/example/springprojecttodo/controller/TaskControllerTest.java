package org.example.springprojecttodo.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class TaskControllerTest extends AbstractControllerTest {
    private static final String URI = "/tasks";

    @Nested
    class GetTasksTests { // TODO 27.06.2024 use more specific assertions

        private static final String URI_GET_TASKS = URI + "/client/2f16a2fe-cd75-4c1c-9d15-233c0bed4d8d";

        @Test
        void getTestDefault() throws Exception {
            final MvcResult result = mockMvc.perform(prepareRequestBuilderGet(URI_GET_TASKS))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            final String content = result.getResponse().getContentAsString();
            System.out.println(content);
            Assertions.assertThat(content).isNotBlank()
                    .startsWith("[{\"id\":\"54a549b1-5905-4fc6-8c7a-9212ccd11c31\",\"title\":\"London Paris New York\""
                            + ",\"completed\":true,\"createdAt\":\"2023-10-29\"}");
        }

        @Test
        void getTestSortDesc() throws Exception {
            final MvcResult result = mockMvc.perform(prepareRequestBuilderGet(URI_GET_TASKS + "?direction=DESC"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            final String content = result.getResponse().getContentAsString();
            System.out.println(content);
            Assertions.assertThat(content).isNotBlank()
                    .startsWith("[{\"id\":\"700df3b8-d613-47eb-befd-0555fae3f3d5\",\"title\":\"Wishmaster\","
                            + "\"completed\":true,\"createdAt\":\"2024-05-24\"}");
        }
    }
}