package org.example.springprojecttodo.controller;

import jakarta.validation.Valid;
import org.example.springprojecttodo.bean.CreateTaskRequestBean;
import org.example.springprojecttodo.service.TaskService;
import org.example.springprojecttodo.shared.MyExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

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
}
