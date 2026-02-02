package org.example.controller;

import org.example.model.Todo;
import org.example.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    @WithMockUser(username = "testuser")
    void index() throws Exception {
        Todo todo1 = new Todo("Target 1", "Desc 1");
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(todo1));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("todos"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void search() throws Exception {
        mockMvc.perform(get("/")
                .param("keyword", "milk"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("todos"));
        
        verify(todoService).searchTodos(eq("milk"), any());
    }

    @Test
    @WithMockUser(username = "testuser")
    void addTodoForm() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-todo"))
                .andExpect(model().attributeExists("todo"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void editTodoForm() throws Exception {
        Long id = 1L;
        Todo todo = new Todo("Task 1", "Desc 1");
        todo.setId(id);
        when(todoService.getTodoById(id)).thenReturn(Optional.of(todo));

        mockMvc.perform(get("/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("add-todo"))
                .andExpect(model().attributeExists("todo"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void addTodo() throws Exception {
        mockMvc.perform(post("/add")
                .with(csrf()) // CSRF token required for POST
                .param("title", "New Task")
                .param("description", "New Desc")
                .param("priority", "HIGH"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(todoService).saveTodo(any(Todo.class));
    }

    @Test
    @WithMockUser(username = "testuser")
    void deleteTodo() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/delete/" + id)) // GET requests are ignored by CSRF by default usually, but if enabled need to check config. Default spring security protects POST.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(todoService).deleteTodo(id);
    }
}
