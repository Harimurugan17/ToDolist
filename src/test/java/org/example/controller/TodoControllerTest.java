package org.example.controller;

import org.example.model.Todo;
import org.example.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    void index() throws Exception {
        Todo todo1 = new Todo("Target 1", "Desc 1");
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(todo1));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("todos"));
    }

    @Test
    void addTodoForm() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-todo"))
                .andExpect(model().attributeExists("todo"));
    }

    @Test
    void addTodo() throws Exception {
        mockMvc.perform(post("/add")
                .param("title", "New Task")
                .param("description", "New Desc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(todoService).saveTodo(any(Todo.class));
    }

    @Test
    void deleteTodo() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(todoService).deleteTodo(id);
    }

    @Test
    void toggleTodo() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/toggle/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(todoService).toggleTodo(id);
    }
}
