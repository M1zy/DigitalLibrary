package com.example.BookLibrary.domains;

import com.example.BookLibrary.domains.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Library extends Essence {
    private String address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "library_id")
    private Set<BookRegistration> bookRegistrations = new HashSet<>();

    public Library(String name,String address,Set<BookRegistration> registrations) {
        super(name);
        this.address = address;
        this.bookRegistrations = registrations;
    }

    public boolean takeBook(Book book){
        for(BookRegistration bookRegistration :
                bookRegistrations){
            if(bookRegistration.getBook() == book){
                if(bookRegistration.getCount()>=1){
                    bookRegistration.setCount(bookRegistration.getCount()-1);
                    return true;
                }
            }
        }
        return false;
    }

    public void returnBook(Book book){
        for(BookRegistration bookRegistration :
                bookRegistrations){
            if(bookRegistration.getBook() == book){
                bookRegistration.setCount(bookRegistration.getCount()+1);
            }
        }
    }

    public Set<Cart> getCartSet(){
        Set<Cart> cartSet = new HashSet<>();
        for (BookRegistration registration:
                bookRegistrations) {
            for (CartRegistration cartReg:
                    registration.getCartRegistrations()) {
                cartSet.add(cartReg.getCart());
            }
        }
        return cartSet;
    }

    public void returnBooks(Set<Book> books){
        for (Book book:
                books) {
            returnBook(book);
        }
    }

    public void toConsole(){
        System.out.println("Id:"+ getId()+"; Name:"+getName()+"; Address:"+ getAddress()+";");
    }

    public Set<Book> getBooks(){
        Set<Book> books = new HashSet<>();
        for (BookRegistration bookRegistration:
                bookRegistrations) {
            books.add(bookRegistration.getBook());
        }
        return books;
    }
}

