package org.example.springprojecttodo.service;

import org.example.springprojecttodo.annotation.LogTime;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    @LogTime
    public String createTask() {
        return "Todo";
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
