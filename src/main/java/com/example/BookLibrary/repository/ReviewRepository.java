package com.example.BookLibrary.repository;

import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Review;
import com.example.BookLibrary.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> getAllByBookAndUser(Book book, User user);

    List<Review> getAllByBook(Book book);
}
