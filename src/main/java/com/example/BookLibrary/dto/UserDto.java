package com.example.BookLibrary.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserDto {
    private Long id;

    @NotEmpty(message = "name must not be empty")
    @Length(min = 6, message = "name's min length 6")
    private String name;

    @NotEmpty(message = "password must not be empty")
    @Length(min = 6, message = "password's min length 6")
    private String password;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "email should be a valid email")
    @Length(min = 6, message = "email's min length 6")
    private String email;

}