package org.example.controller;

import org.example.model.Todo;
import org.example.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword,
                        @RequestParam(required = false) String filter,
                        Model model) {
        if ((keyword != null && !keyword.isEmpty()) || (filter != null && !filter.isEmpty())) {
             model.addAttribute("todos", todoService.searchTodos(keyword, filter));
             model.addAttribute("keyword", keyword);
             model.addAttribute("filter", filter);
        } else {
            model.addAttribute("todos", todoService.getAllTodos());
        }
        return "index";
    }

    @GetMapping("/add")
    public String  addTodoForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "add-todo";
    }

    @PostMapping("/add")
    public String addTodo(@ModelAttribute Todo todo) {
        if (todo.getPriority() == null) {
            todo.setPriority(org.example.model.Priority.MEDIUM);
        }
        todoService.saveTodo(todo);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editTodoForm(@PathVariable Long id, Model model) {
        java.util.Optional<Todo> todo = todoService.getTodoById(id);
        if (todo.isPresent()) {
            model.addAttribute("todo", todo.get());
            return "add-todo";
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/";
    }

    @GetMapping("/toggle/{id}")
    public String toggleTodo(@PathVariable Long id) {
        todoService.toggleTodo(id);
        return "redirect:/";
    }
}
