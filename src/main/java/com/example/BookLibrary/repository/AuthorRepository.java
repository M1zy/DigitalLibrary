package com.example.BookLibrary.repository;


import com.example.BookLibrary.domains.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAuthorsByNameContains(String name);
}

