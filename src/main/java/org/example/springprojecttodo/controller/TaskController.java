package org.example.springprojecttodo.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.example.springprojecttodo.bean.TasksResponseBean;
import org.example.springprojecttodo.bean.CreateTaskRequestBean;
import org.example.springprojecttodo.context.TaskContext;
import org.example.springprojecttodo.service.TaskService;
import org.example.springprojecttodo.shared.MyExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Resource
    private TaskContext context;

    @Autowired
    TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/client/{clientId}")
    public UUID createTask(
            @PathVariable final UUID clientId,
            @Valid @RequestBody final CreateTaskRequestBean createTaskRequestBean,
            final BindingResult bindingResult
    ) {
        MyExceptionUtils.checkValidationResult(bindingResult);
        return taskService.createTask(clientId, createTaskRequestBean);
    }

    @GetMapping("/client/{clientId}")
    public List<TasksResponseBean> getTasks(
            @PathVariable final UUID clientId,
            @RequestParam(defaultValue = "false") final boolean completed,
            @RequestParam(defaultValue = "createdAt") final String sortField,
            @RequestParam(defaultValue = "ASC") final Sort.Direction direction,
            @RequestParam(defaultValue = "0") final Integer page,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        context.setPageable(PageRequest.of(page, size, Sort.by(direction, sortField)));
        context.setClientId(clientId);
        context.setCompleted(completed);
        return taskService.getTasks();
    }

    @GetMapping("/client/{clientId}/task/{taskId}")
    public TasksResponseBean getTask(
            @PathVariable final UUID clientId,
            @PathVariable final UUID taskId
    ) {
        return taskService.getTasks(clientId, taskId);
    }

    @DeleteMapping("/client/{clientId}/task/{taskId}")
    public boolean getTasks(
            @PathVariable final UUID clientId,
            @PathVariable final UUID taskId
    ) {
        return taskService.deleteTask(clientId, taskId);
    }

    @PutMapping("/client/{clientId}/task/{taskId}")
    public TasksResponseBean updateTask(
            @PathVariable final UUID clientId,
            @PathVariable final UUID taskId,
            @Valid @RequestBody final CreateTaskRequestBean createTaskRequestBean,
            final BindingResult bindingResult
    ) {
        MyExceptionUtils.checkValidationResult(bindingResult);
        return taskService.updateTask(clientId, taskId, createTaskRequestBean);
    }
}
