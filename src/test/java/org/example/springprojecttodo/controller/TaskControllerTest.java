package org.example.springprojecttodo.controller;

import org.assertj.core.api.Assertions;
import org.example.springprojecttodo.bean.CreateTaskRequestBean;
import org.example.springprojecttodo.bean.TasksResponseBean;
import org.example.springprojecttodo.model.ClientStatus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;

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
                    .startsWith("[{\"id\":\"1c8e43d9-2bff-4cf0-a77e-6de63b9dd2fe\",\"title\":\"Taxi\","
                            + "\"completed\":false,\"createdAt\":\"2024-03-30\"}");
        }

        @Test
        void getTestSortDesc() throws Exception {
            final MvcResult result = mockMvc.perform(prepareRequestBuilderGet(URI_GET_TASKS + "?direction=DESC"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            final String content = result.getResponse().getContentAsString();
            System.out.println(content);
            Assertions.assertThat(content).isNotBlank()
                    .startsWith("[{\"id\":\"0cea7c4a-8489-40e0-8831-9684a03d789c\","
                            + "\"title\":\"Year of the Wolf, The (Suden vuosi)\"");
        }
    }

    @Nested
    class GetTaskTests {
        private static final String URI_GET_TASK = URI + "/client/2f16a2fe-cd75-4c1c-9d15-233c0bed4d8d"
                + "/task/1c8e43d9-2bff-4cf0-a77e-6de63b9dd2fe";

        @Test
        void getTestDefault() throws Exception {
            final MvcResult result = mockMvc.perform(prepareRequestBuilderGet(URI_GET_TASK))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            final String content = result.getResponse().getContentAsString();
            final TasksResponseBean responseBean = mapper.readValue(content, TasksResponseBean.class);
            Assertions.assertThat(responseBean)
                    .extracting(
                            TasksResponseBean::id,
                            TasksResponseBean::title,
                            TasksResponseBean::completed,
                            TasksResponseBean::createdAt
                    ).contains(
                            UUID.fromString("1c8e43d9-2bff-4cf0-a77e-6de63b9dd2fe"),
                            "Taxi",
                            false,
                            LocalDate.of(2024, 3, 30)
                    );
        }

        @Test
        void getTestError() throws Exception {
            mockMvc.perform(prepareRequestBuilderGet(URI_GET_TASK.replaceAll("1", "2")))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }
    }

    @Nested
    class DeleteTaskTests {
        private static final String URI_GET_TASK = URI + "/client/2f16a2fe-cd75-4c1c-9d15-233c0bed4d8d"
                + "/task/1c8e43d9-2bff-4cf0-a77e-6de63b9dd2fe";

        @Test
        void getTestDefault() throws Exception {
            final MvcResult result = mockMvc.perform(prepareRequestBuilderDelete(URI_GET_TASK))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            final String content = result.getResponse().getContentAsString();
            Assertions.assertThat(content).isEqualTo("true");

            final MvcResult result2 = mockMvc.perform(prepareRequestBuilderGet(URI_GET_TASK))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            final String content2 = result2.getResponse().getContentAsString();
            final TasksResponseBean responseBean = mapper.readValue(content2, TasksResponseBean.class);
            Assertions.assertThat(responseBean.completed()).isTrue();
        }

        @Test
        void getTestError() throws Exception {
            final MvcResult result = mockMvc.perform(prepareRequestBuilderDelete(URI_GET_TASK.replaceAll("1", "2")))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            final String content = result.getResponse().getContentAsString();
            Assertions.assertThat(content).isEqualTo("false");

            final MvcResult result2 = mockMvc.perform(prepareRequestBuilderGet(URI_GET_TASK))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            final String content2 = result2.getResponse().getContentAsString();
            final TasksResponseBean responseBean = mapper.readValue(content2, TasksResponseBean.class);
            Assertions.assertThat(responseBean.completed()).isFalse();
        }

        @Nested
        class UpdateTaskTests {
            private static final String URI_GET_TASK = URI + "/client/2f16a2fe-cd75-4c1c-9d15-233c0bed4d8d"
                    + "/task/1c8e43d9-2bff-4cf0-a77e-6de63b9dd2fe";

            @Test
            void getTestDefault() throws Exception {
                final CreateTaskRequestBean requestBean = new CreateTaskRequestBean("New title", "New description");

                final MvcResult result = mockMvc.perform(prepareRequestBuilderPut(URI_GET_TASK, requestBean))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();


                final String content = result.getResponse().getContentAsString();
                final TasksResponseBean responseBean = mapper.readValue(content, TasksResponseBean.class);
                Assertions.assertThat(responseBean)
                        .extracting(
                                TasksResponseBean::id,
                                TasksResponseBean::title,
                                TasksResponseBean::completed,
                                TasksResponseBean::createdAt
                        ).contains(
                                UUID.fromString("1c8e43d9-2bff-4cf0-a77e-6de63b9dd2fe"),
                                "New title",
                                false,
                                LocalDate.of(2024, 3, 30)
                        );
            }
        }
    }
}