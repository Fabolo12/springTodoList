package org.example.springprojecttodo.service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.example.springprojecttodo.annotation.LogTime;
import org.example.springprojecttodo.bean.CreateTaskRequestBean;
import org.example.springprojecttodo.bean.TasksResponseBean;
import org.example.springprojecttodo.context.TaskContext;
import org.example.springprojecttodo.controller.TaskController;
import org.example.springprojecttodo.exeption.EntityNotFound;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.Task;
import org.example.springprojecttodo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final ClientServiceJpa clientService;

    private final TaskRepository taskRepository;

    @Resource
    private TaskContext context;

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

    public TasksResponseBean updateTask(
            final UUID clientId,
            final UUID taskId,
            final CreateTaskRequestBean createTaskRequestBean
    ) {
        return taskRepository.findByIdAndClientId(taskId, clientId)
                .map(task -> {
                    if (createTaskRequestBean.title() != null) {
                        task.setTitle(createTaskRequestBean.title());
                    }

                    if (createTaskRequestBean.description() != null) {
                        task.setDescription(createTaskRequestBean.description());
                    }
                    taskRepository.save(task);

                    return map(task);
                })
                .orElseThrow(() -> new EntityNotFound("Task not found with id: " + taskId));
    }

    public boolean deleteTask(
            final UUID clientId,
            final UUID taskId
    ) {
        return taskRepository.deleteTask(taskId, clientId) > 0;
    }

    public List<TasksResponseBean> getTasks() {
        return taskRepository.findAllByClientIdAndCompleted(
                        context.getClientId(),
                        context.isCompleted(),
                        context.getPageable()
                ).stream()
                .map(this::map)
                .toList();
    }

    public TasksResponseBean getTasks(
            final UUID clientId,
            final UUID taskId
    ) {
        return taskRepository.findByIdAndClientId(taskId, clientId)
                .map(this::map)
                .orElseThrow(() -> new EntityNotFound("Task not found with id: " + taskId));
    }

    private TasksResponseBean map(final Task task) {
        return new TasksResponseBean(
                task.getId(),
                task.getTitle(),
                task.isCompleted(),
                task.getCreatedAt()
        );
    }


}
