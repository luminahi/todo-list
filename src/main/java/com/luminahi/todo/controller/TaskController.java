package com.luminahi.todo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luminahi.todo.model.Task;
import com.luminahi.todo.repository.TaskRepository;

@RestController
public class TaskController {
    
    TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping("/")
    public List<Task> getAll() {
        return repository.findAll();
    }
}
