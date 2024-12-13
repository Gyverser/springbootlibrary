package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    @NotBlank
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 letters")
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    @NotBlank
    @Size(min = 3, message = "Password must have at least 3 letters")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
