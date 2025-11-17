package com.example.spring_todo_api.repository;

import com.example.spring_todo_api.entity.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    public boolean existsByTitle(String title);
    public List<Todo> findTodosByUserId(Integer userId);
}
