package org.example.service;

import org.example.model.Todo;
import org.example.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void getAllTodos() {
        Todo todo1 = new Todo("Task 1", "Desc 1");
        Todo todo2 = new Todo("Task 2", "Desc 2");
        when(todoRepository.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        List<Todo> result = todoService.getAllTodos();

        assertEquals(2, result.size());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void saveTodo() {
        Todo todo = new Todo("Task 1", "Desc 1");
        
        todoService.saveTodo(todo);

        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void deleteTodo() {
        Long id = 1L;

        todoService.deleteTodo(id);

        verify(todoRepository, times(1)).deleteById(id);
    }

    @Test
    void toggleTodo() {
        Long id = 1L;
        Todo todo = new Todo("Task 1", "Desc 1");
        // Initial state is false
        assertFalse(todo.isCompleted());
        todo.setId(id);

        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        todoService.toggleTodo(id);

        assertTrue(todo.isCompleted());
        verify(todoRepository, times(1)).save(todo);
    }
}
