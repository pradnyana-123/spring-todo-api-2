package com.example.spring_todo_api.repository;

import com.example.spring_todo_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public boolean existsByUsername(String username);
}
