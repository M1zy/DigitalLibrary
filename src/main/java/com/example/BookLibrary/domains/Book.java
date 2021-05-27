package com.example.BookLibrary.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book extends Essence {

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    private String image;
    private Integer year;
    private String description;
    private String path;
    private String pdf;

    @Min(value = 1)
    @NotNull
    private Integer pages;

    @ManyToOne
    private User adderUser;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private Set<BookRegistration> bookRegistrations = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "archive",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> usersArchive = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private Set<Rating> ratings = new HashSet<>();

    public Book(String name,Set<Author> authors, Integer year,
                String description,Set<BookRegistration> bookRegistrations) {
        super(name);
        this.authors = authors;
        this.year = year;
        this.description = description;
        this.bookRegistrations = bookRegistrations;
    }

    public void addRegistration(BookRegistration registration){
        bookRegistrations.add(registration);
    }

    public boolean takeBook(Library library){
        for(BookRegistration bookRegistration :
                bookRegistrations){
            if(bookRegistration.getLibrary() == library){
                if(bookRegistration.getCount()>=1){
                    bookRegistrations.remove(bookRegistration);
                    bookRegistrations.add(new BookRegistration(bookRegistration.getLibrary(),
                            bookRegistration.getBook(),bookRegistration.getCount()-1));
                    return true;
                }
            }
        }
        return false;
    }

    public void addAuthor(Author author){
        Set<Book> books = author.getBooks();
        books.add(this);
        author.setBooks(books);
        authors.add(author);
    }

    public void addGenre(Genre genre){
        this.genres.add(genre);
    }

    public void removeGenre(Genre genre){
        this.genres.remove(genre);
    }

    public void toConsole(){
        System.out.println("Id:"+ getId()+"; Name:"+getName()+"; Year:"+ getYear()+";");
    }

    public Set<Library> getLibraries(){
        Set<Library> libraries = new HashSet<>();
        for (BookRegistration bookRegistration:
                bookRegistrations) {
            libraries.add(bookRegistration.getLibrary());
        }
        return libraries;
    }

    public void addBookToArchive(User user){
        this.usersArchive.add(user);
    }

    public void removeBookFromArchive(User user){
        this.usersArchive.remove(user);
    }

    public String getAuthorsToString(){
        if(getAuthors().isEmpty())return "No author";
        return getAuthors().stream().
                map(Essence::getName).collect(Collectors.toSet()).toString();
    }

    public String getLibrariesToString(){
        if(getAuthors().isEmpty())return "No library";
        return getLibraries().stream().
                map(Library::getAddress).collect(Collectors.toSet()).toString();
    }
}