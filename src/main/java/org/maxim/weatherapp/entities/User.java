package org.maxim.weatherapp.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "login should not be empty")
    @Email(message = "email should be valid")
    @Column(name = "login", unique = true)
    private String login;

    @NotEmpty(message = "password should not be empty")
    @Size(min = 8, max = 64, message = "password size should be more 8 character and less 64 character")
    @Column(name = "password")
    private String password;
}