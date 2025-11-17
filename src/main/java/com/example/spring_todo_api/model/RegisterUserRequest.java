package com.example.spring_todo_api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserRequest {

    @NotBlank
    @Size(max=100)
    private String username;

    @NotBlank
    @Email
    @Size(max=100)
    private String email;

    @NotBlank
    @Size(max=255)
    private String password;
}
