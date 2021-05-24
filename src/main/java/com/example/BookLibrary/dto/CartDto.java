package com.example.BookLibrary.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class CartDto {
    private Long id;

    @NotNull(message = "userID must not be empty")
    private Long userId;

    private Set<Long> registrationsIds;
}
