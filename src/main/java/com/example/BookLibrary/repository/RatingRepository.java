package com.example.BookLibrary.repository;

import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Rating;
import com.example.BookLibrary.domains.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    Rating getByUserAndBook(User user, Book book);
    List<Rating> getAllByBook(Book book);
}
