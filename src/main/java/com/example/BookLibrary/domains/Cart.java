package com.example.BookLibrary.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="carts")
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private Set<CartRegistration> cartRegistrations = new HashSet<>();

    @NotNull
    private Status status = Status.PROCESSING;


    public void setStatus(Status status){
        this.status = status;
    }

    public Cart(Set<CartRegistration> registrations, User user){
        this.cartRegistrations = registrations;
        this.user = user;
    }

    public void addCartRegistration(CartRegistration registration){
        registration.setCart(this);
        this.cartRegistrations.add(registration);
    }

    public void removeCartRegistration(CartRegistration registration){this.cartRegistrations.remove(registration);}
}
