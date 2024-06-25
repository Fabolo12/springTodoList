package org.example.springprojecttodo.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.example.springprojecttodo.annotation.LogTime;
import org.example.springprojecttodo.bean.CreateTaskRequestBean;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.Task;
import org.example.springprojecttodo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TaskService {

    private final ClientServiceJpa clientService;

    private final TaskRepository taskRepository;


    TaskService(final ClientServiceJpa clientService, final TaskRepository taskRepository) {
        this.clientService = clientService;
        this.taskRepository = taskRepository;
    }

    @LogTime
    @Transactional
    public UUID createTask(final UUID clientId, final @Valid CreateTaskRequestBean createTaskRequestBean) {
        final Client client = clientService.getClient(clientId);
        final Task task = Task.builder()
                .title(createTaskRequestBean.title())
                .description(createTaskRequestBean.description())
                .createdAt(LocalDate.now())
                .client(client)
                .build();
        taskRepository.save(task);
        return task.getId();
    }

    public String editTask() {
        return "Todo";
    }

    public String deleteTask() {
        return "Todo";
    }

    public String getTask() {
        return "Todo";
    }

    public String getTasks() {
        return "Todo";
    }


}
