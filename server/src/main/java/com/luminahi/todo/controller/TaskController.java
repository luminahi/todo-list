package com.luminahi.todo.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.DataBinder;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValues;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.luminahi.todo.model.Task;
import com.luminahi.todo.repository.TaskRepository;

@RestController
public class TaskController {
        
    TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping("/tasks")
    public Page<Task> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    @PostMapping("/tasks")
    public void newTask(@RequestBody Task task) {
        if (task.getTitle() == null) return;
        if (task.getDescription() == null) return;
        if (task.getIsDone() == null) return;
        
        repository.save(task);
    }
    
    @PutMapping("/tasks/{id}")
    public void replaceTask(@RequestBody Task newTask, @PathVariable Long id) {
        repository.findById(id)
            .map((task) -> {
                task.setTitle(newTask.getTitle());
                task.setDescription(newTask.getDescription());
                task.setIsDone(newTask.getIsDone());
                return repository.save(task);
            }).orElseGet(() -> repository.save(newTask));
    }
    
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
    @PatchMapping("/tasks/{id}")
    public void test(@RequestBody Task sourceTask, @PathVariable Long id) throws NotFoundException {
        repository.findById(id).map(targetTask -> {
            applyPatch(targetTask, sourceTask);
            targetTask.setId(id);
            return repository.save(targetTask);
        }).orElseThrow(() -> new NotFoundException());
    }
    
    public void applyPatch(Task target, Task source) {
        BeanWrapper wrapperSource = PropertyAccessorFactory.forBeanPropertyAccess(source);
        BeanWrapper wrapperTarget = PropertyAccessorFactory.forBeanPropertyAccess(target);
        
        for (PropertyDescriptor descriptor : wrapperSource.getPropertyDescriptors()) {
            String propertyName = descriptor.getName();
            Object providedObject = wrapperSource.getPropertyValue(propertyName);
            
            if (!propertyName.equals("class") && providedObject != null) {
                wrapperTarget.setPropertyValue(propertyName, providedObject);
            }
        }
    }
    
}
