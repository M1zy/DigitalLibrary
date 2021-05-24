package com.example.BookLibrary.repository;

import com.example.BookLibrary.domains.Library;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends CrudRepository<Library, Long> {
    List<Library> findLibrariesByNameContains(String name);
}
