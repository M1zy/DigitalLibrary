package com.example.BookLibrary.service;

import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Library;
import com.example.BookLibrary.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryService {
    @Autowired
    LibraryRepository libraryRepository;

    public void save(Library library){
        libraryRepository.save(library);
    }

    public List<Library> listAll() {
        return (List<Library>) libraryRepository.findAll();
    }

    public Library get(Long id) {
        return libraryRepository.findById(id).get();
    }

    public void delete(Long id) {
        libraryRepository.deleteById(id);
    }

    public List<Library> listByName(String name){
        return libraryRepository.findLibrariesByNameContains(name);
    }

    public boolean exist(Long id){
        return libraryRepository.existsById(id);
    }

    public Set<Book> bookSet(Long id){return libraryRepository.findById(id).get().getBooks();}

    public void allLibrariesToConsole(){
        System.out.println("LIBRARIES:");
        for (Library library:
                listAll()) {
            System.out.print("Library-");
            library.toConsole();
        }
    }

    public void commandToConsole(String[] args){
        for(int i=0;i<args.length;i++) {
            switch (args[i]) {
                case "all": {
                    allLibrariesToConsole();
                    break;
                }
            }
        }
    }
}
