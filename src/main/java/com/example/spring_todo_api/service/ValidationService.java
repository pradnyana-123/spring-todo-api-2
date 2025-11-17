package com.example.spring_todo_api.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidationService {

    @Autowired
    private Validator validator;

    public void validate(Object target) {
        Set<ConstraintViolation<Object>> validated = validator.validate(target);
        if (!validated.isEmpty()) {
            throw new ConstraintViolationException(validated);
        }
    }
}
