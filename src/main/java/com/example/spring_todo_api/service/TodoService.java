package com.example.spring_todo_api.service;

import com.example.spring_todo_api.entity.Todo;
import com.example.spring_todo_api.entity.User;
import com.example.spring_todo_api.model.CreateTodoRequest;
import com.example.spring_todo_api.model.TodoResponse;
import com.example.spring_todo_api.model.UpdateTodoRequest;
import com.example.spring_todo_api.repository.TodoRepository;
import com.example.spring_todo_api.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ValidationService validator;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void create(Integer userId, CreateTodoRequest data) {
        validator.validate(data);

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        )
                );

        boolean existingTodo = todoRepository.existsByTitle(data.getTitle());

        if (existingTodo) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Todo already exists"
            );
        }

        Todo todo = new Todo();

        todo.setUser(user);
        todo.setTitle(data.getTitle());
        todo.setDescription(data.getDescription());

        todoRepository.save(todo);
    }

    public List<Todo> getsFromUser(Integer userId) {
        List<Todo> todos = todoRepository.findTodosByUserId(userId);

        if (todos.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No todos found"
            );
        }

        return todos;
    }

    public TodoResponse getFromUser(Integer todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No todo found"));

        return TodoResponse.builder().title(todo.getTitle()).description(todo.getDescription()).completed(todo.getCompleted()).build();
    }

    @Transactional
    public TodoResponse updateTodo(Integer todoId, UpdateTodoRequest data) {
        validator.validate(data);

        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        todo.setTitle(data.getTitle());
        todo.setDescription(data.getDescription());
        todo.setCompleted(data.getCompleted());

        todoRepository.save(todo);

        return TodoResponse.builder().title(todo.getTitle()).description(todo.getDescription()).completed(todo.getCompleted()).build();
    }

    public void delete(Integer todoId,  Integer userId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        if(todo.getUser().getId().equals(userId)) {
            todoRepository.delete(todo);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this todo");
        }
    }

}
