package org.example.service;

import org.example.model.Todo;
import org.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    
    public List<Todo> searchTodos(String keyword, String filter) {
        if (keyword != null && !keyword.isEmpty()) {
            if ("completed".equalsIgnoreCase(filter)) {
                return todoRepository.findByTitleContainingIgnoreCaseAndCompleted(keyword, true);
            } else if ("pending".equalsIgnoreCase(filter)) {
                return todoRepository.findByTitleContainingIgnoreCaseAndCompleted(keyword, false);
            }
            return todoRepository.findByTitleContainingIgnoreCase(keyword);
        } else {
            if ("completed".equalsIgnoreCase(filter)) {
                return todoRepository.findByCompleted(true);
            } else if ("pending".equalsIgnoreCase(filter)) {
                return todoRepository.findByCompleted(false);
            }
        }
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public void saveTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public void toggleTodo(Long id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
        }
    }
}
