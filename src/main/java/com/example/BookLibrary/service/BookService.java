package com.example.BookLibrary.service;

import com.example.BookLibrary.domains.Author;
import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Genre;
import com.example.BookLibrary.domains.Library;
import com.example.BookLibrary.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public void save(Book book){
        bookRepository.save(book);
    }

    public List<Book> listAll() {
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> getBooks(int currentPage, int recordsPerPage, List<Book> bookList){
        if(currentPage==0)currentPage=1;
        List<Book> books = new ArrayList<>();
        int start = currentPage * recordsPerPage - recordsPerPage;
        if(start<bookList.size()) {
            int end = Math.min(currentPage * recordsPerPage, bookList.size());
            try {
                for (int i = start; i < end; i++) {
                    books.add(bookList.get( i));
                }
            } catch (Exception ex) {
                Logger.getLogger(BookService.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
        return  books;
    }

    public List<Book> findBooks(int currentPage, int recordsPerPage) {
        return getBooks(currentPage,recordsPerPage,listAll());
    }

    public List<Book> findBooksByGenre(int currentPage, int recordsPerPage, Genre genre){
        return getBooks(currentPage,recordsPerPage,listByGenre(genre));
    }

    public Book get(Long id) {
        return bookRepository.findById(id).get();
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public Set<Library> listLibraries(Long id){
        return bookRepository.findById(id).get().getLibraries();
    }

    public List<Book> listByName(String filter){
        return bookRepository.findBooksByNameContains(filter);
    }

    public List<Book> listByAuthor(Author author){
        return bookRepository.findBooksByAuthorsContains(author);
    }

    public List<Book> listByGenre(Genre genre){
        return bookRepository.findBooksByGenresContains(genre);
    }

    public boolean exist(Long id){
        return bookRepository.existsById(id);
    }

    private void booksToConsole(List<Book> books) {
        System.out.println("Book:");
        for (Book book :
                books) {
            System.out.print("Book-");
            book.toConsole();
        }
    }

    public void commandToConsole(String[] args){
        for(int i=0;i<args.length;i++) {
            switch (args[i]) {
                case "all": {
                    booksToConsole(listAll());
                    break;
                }
                case "name": {
                    try {
                        booksToConsole(listByName(args[i++]));
                    }
                    catch (Exception ex){
                        booksToConsole(listAll());
                    }
                    break;
                }
            }
        }
    }
}
