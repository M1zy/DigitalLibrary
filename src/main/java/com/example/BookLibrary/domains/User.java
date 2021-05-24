package com.example.BookLibrary.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class User extends Essence {
    private String email;

    private String password;

    private Boolean authority = Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Set<Cart> cartSet =new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "archive",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> booksArchive = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Set<Rating> rates = new HashSet<>();

    public User(String name, String password, String email){
        super(name);
        this.email=email;
        this.password=password;
    }

    public void addCart(Cart cart){
        this.cartSet.add(cart);
    }

    public void removeCart(Cart cart){
        this.cartSet.remove(cart);
    }

    public void addBookToArchive(Book book){
        this.booksArchive.add(book);
    }

    public void removeBookFromArchive(Book book){
        this.booksArchive.remove(book);
    }

    public void toConsole(){
        System.out.println("Id:"+ getId()+"; Name:"+getName()+"; Email:"+ getEmail()+"; Password:"+getPassword());
    }
}