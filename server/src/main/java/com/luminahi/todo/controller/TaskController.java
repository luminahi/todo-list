package com.luminahi.todo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luminahi.todo.model.Task;
import com.luminahi.todo.repository.TaskRepository;

@RestController
public class TaskController {
        
    TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping("/tasks")
    public List<Task> getAll() {
        return repository.findAll();
    }
    
    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable Long id) {
        return repository.findById(id).orElse(new Task());
    }

    @PostMapping("/tasks")
    public void newTask(@RequestBody Task task) {
        if (task.getTitle() == null) return;
        if (task.getDescription() == null) return;
        if (task.getIsDone() == null) return;
        repository.save(task);
    }
    
    @PutMapping("/tasks/{id}")
    public void updateTask(@RequestBody Task newTask, @PathVariable Long id) {
        Task oldTask = repository.getReferenceById(id);
        if (newTask.getIsDone() != null) 
            oldTask.setIsDone(newTask.getIsDone());
        if (newTask.getTitle() != null) 
            oldTask.setTitle(newTask.getTitle());
        if (newTask.getDescription() != null)
            oldTask.setDescription(newTask.getDescription());
        repository.save(oldTask);
    }
    
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
    
}
