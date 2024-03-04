package com.luminahi.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luminahi.todo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
