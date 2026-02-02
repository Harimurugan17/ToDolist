package org.example.service;

import org.example.model.Priority;
import org.example.model.Todo;
import org.example.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
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
    void searchTodos_KeywordOnly() {
        Todo todo = new Todo("Buy Milk", "Groceries");
        when(todoRepository.findByTitleContainingIgnoreCase("Milk")).thenReturn(Collections.singletonList(todo));

        List<Todo> result = todoService.searchTodos("Milk", "");

        assertEquals(1, result.size());
        verify(todoRepository).findByTitleContainingIgnoreCase("Milk");
    }

    @Test
    void searchTodos_FilterCompleted() {
        when(todoRepository.findByCompleted(true)).thenReturn(Collections.emptyList());

        todoService.searchTodos(null, "completed");

        verify(todoRepository).findByCompleted(true);
    }
    
    @Test
    void getTodoById() {
        Long id = 1L;
        Todo todo = new Todo("Task 1", "Desc 1");
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        Optional<Todo> result = todoService.getTodoById(id);

        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());
    }

    @Test
    void saveTodo() {
        Todo todo = new Todo("Task 1", "Desc 1", LocalDate.now(), Priority.HIGH);
        
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
        assertFalse(todo.isCompleted());
        todo.setId(id);

        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        todoService.toggleTodo(id);

        assertTrue(todo.isCompleted());
        verify(todoRepository, times(1)).save(todo);
    }
}
