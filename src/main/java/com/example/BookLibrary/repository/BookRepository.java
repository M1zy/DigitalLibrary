package com.example.BookLibrary.repository;

import com.example.BookLibrary.domains.Author;
import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Genre;
import com.example.BookLibrary.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findBooksByNameContainsAndVerifiedTrue(String name);

    List<Book> findBooksByVerifiedTrue();

    List<Book> findBooksByVerifiedFalse();

    List<Book> findBooksByAuthorsContainsAndVerifiedTrue(Author author);

    List<Book> findBooksByGenresContainsAndVerifiedTrue(Genre genre);

    List<Book> findBooksByAdderUserAndVerifiedTrue(User user);

    List<Book> findBooksByUsersArchiveContainsAndVerifiedTrue(User user);

    Book findBookByIdAndVerifiedTrue(Long index);
}
