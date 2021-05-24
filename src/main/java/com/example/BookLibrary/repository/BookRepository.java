package com.example.BookLibrary.repository;

import com.example.BookLibrary.domains.Author;
import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findBooksByNameContains(String name);

    List<Book> findBooksByAuthorsContains(Author author);

    List<Book> findBooksByGenresContains(Genre genre);

    Book findBookById(Long index);
}
