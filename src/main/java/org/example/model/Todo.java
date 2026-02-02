package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean completed;

    @jakarta.persistence.Column(name = "due_date")
    private java.time.LocalDate dueDate;

    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
    private Priority priority;

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "user_id")
    private User user;

    public Todo() {
    }

    public Todo(String title, String description, java.time.LocalDate dueDate, Priority priority, User user) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.dueDate = dueDate;
        this.priority = priority;
        this.user = user;
    }

    public Todo(String title, String description, java.time.LocalDate dueDate, Priority priority) {
        this(title, description, dueDate, priority, null);
    }

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.priority = Priority.MEDIUM; // Default
    }

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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public java.time.LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(java.time.LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
