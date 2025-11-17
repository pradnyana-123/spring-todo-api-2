package com.example.spring_todo_api.controller;

import com.example.spring_todo_api.entity.Todo;
import com.example.spring_todo_api.model.CreateTodoRequest;
import com.example.spring_todo_api.model.TodoResponse;
import com.example.spring_todo_api.model.UpdateTodoRequest;
import com.example.spring_todo_api.model.WebResponse;
import com.example.spring_todo_api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping(path = "/api/users/{userId}/todos")
    public ResponseEntity<WebResponse<Object>> create(@RequestBody CreateTodoRequest data, @PathVariable Integer userId) {
        todoService.create(userId, data);

        return new ResponseEntity<>(WebResponse.builder().data("Todo created").status(201).build(), HttpStatus.OK);
    }


    @GetMapping(path = "/api/users/{userId}/todos")
    public ResponseEntity<WebResponse<Object>> getAll(@PathVariable Integer userId) {
        List<Todo> todos = todoService.getsFromUser(userId);

        return new ResponseEntity<>(WebResponse.builder().data("Todo resolved").status(200).data(todos).build(), HttpStatus.OK);
    }


    @GetMapping(path = "/api/todos/{todoId}")
    public ResponseEntity<WebResponse<Object>> getOne(@PathVariable Integer todoId) {
        TodoResponse todo = todoService.getFromUser(todoId);

        return new ResponseEntity<>(WebResponse.builder().data("Todo resolved").status(200).data(todo).build(), HttpStatus.OK);
    }

    @PatchMapping(path = "/api/todos/{todoId}")
    public ResponseEntity<WebResponse<Object>> update(@PathVariable Integer todoId, @RequestBody UpdateTodoRequest data) {

        TodoResponse todoResponse = todoService.updateTodo(todoId, data);

        return new ResponseEntity<>(WebResponse.builder().message("Todo updated").status(200).data(todoResponse).build(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/todos/{todoId}/user/{userId}")
    public ResponseEntity<WebResponse<Object>> delete(@PathVariable Integer todoId, @PathVariable Integer userId) {
        todoService.delete(todoId, userId);

        return new ResponseEntity<>(WebResponse.builder().message("Todo deleted").status(200).build(), HttpStatus.OK);
    }

}
