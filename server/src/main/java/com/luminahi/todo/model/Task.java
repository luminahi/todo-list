package com.luminahi.todo.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 32)
    private String title;
 
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private Boolean isDone;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getIsDone() {
        return isDone;
    }
    
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
    
    @Override
    public String toString() {
        return String.format("Title: %s\nDescription: %s\nID: %s\n", title, description, id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, isDone, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        return Objects.equals(description, other.description) && Objects.equals(id, other.id) && isDone == other.isDone
                && Objects.equals(title, other.title);
    }
}
