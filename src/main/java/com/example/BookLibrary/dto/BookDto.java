package com.example.BookLibrary.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class BookDto {
    private Long id;

    @NotEmpty(message = "name must not be empty")
    @Length(min = 5, message ="Minimal length of name is 5 symbols" )
    private String name;

    private Integer pages;

    private Set<Long> authorIds = new HashSet<>();

    private Integer year;

    @NotEmpty(message = "description must not be empty")
    @Length(min = 10, message = "Minimal length of description is 10 symbols")
    private String description;
}
