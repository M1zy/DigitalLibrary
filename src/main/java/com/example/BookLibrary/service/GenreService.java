package com.example.BookLibrary.service;

import com.example.BookLibrary.domains.Author;
import com.example.BookLibrary.domains.Genre;
import com.example.BookLibrary.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public void save(Genre genre){
        genreRepository.save(genre);
    }

    public List<Genre> listAll() {
        return (List<Genre>) genreRepository.findAll();
    }

    public Genre get(Long id) {
        return genreRepository.findById(id).get();
    }

    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    public boolean exist(Long id){
        return genreRepository.existsById(id);
    }
}
