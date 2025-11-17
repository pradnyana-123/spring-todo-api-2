package com.example.spring_todo_api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTodoRequest {

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String description;

}
