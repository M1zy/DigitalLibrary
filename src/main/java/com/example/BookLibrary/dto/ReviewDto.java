package com.example.BookLibrary.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewDto {
    private Long id;

    @NotNull(message = "Review must not be empty")
    @Min(value = 10,message = "Review must has at least 10 symbols")
    private String text;

    @NotNull(message = "userID must not be empty")
    private Long userId;

    @NotNull(message = "bookID must not be empty")
    private Long bookId;

    public ReviewDto(@NotNull(message = "Review must not be empty") @Min(value = 10, message = "Review must has at least 10 symbols") String text, @NotNull(message = "userID must not be empty") Long userId, @NotNull(message = "bookID must not be empty") Long bookId) {
        this.text = text;
        this.userId = userId;
        this.bookId = bookId;
    }
}
