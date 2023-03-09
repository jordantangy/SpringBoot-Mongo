package com.example.springbootmongo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @NotBlank(message = "The username field is required")
    private String username;
    @Email(message = "Enter a valid email address")
    private String email;
    @NotBlank(message = "The password field is required")
    private String password;


}
