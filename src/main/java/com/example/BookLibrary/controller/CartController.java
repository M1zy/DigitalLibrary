package com.example.BookLibrary.controller;

import com.example.BookLibrary.domains.*;
import com.example.BookLibrary.dto.CartDto;
import com.example.BookLibrary.dto.CartRegistrationDto;
import com.example.BookLibrary.exception.RecordNotFoundException;
import com.example.BookLibrary.mapper.Mapper;
import com.example.BookLibrary.service.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@Api(value="Cart", description="Operations with cart")
@RequiredArgsConstructor
@Log4j2
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private CartRegistrationService cartRegistrationService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private Mapper mapper = new Mapper();

    @RequestMapping(value = "/addCart", method = RequestMethod.POST)
    public ResponseEntity<?> addCart(@Valid @RequestBody CartDto cartDto) {
        Cart cart = mapper.convertToEntity(cartDto);
        cartService.save(cart);
        return new ResponseEntity<>("Cart was created", HttpStatus.OK);
    }

    @RequestMapping(value = "/returnCart", method = RequestMethod.PUT)
    public ResponseEntity<?> returnCart(@Valid @RequestBody CartDto cartDto) {
        try {
            Cart oldCart = mapper.convertToEntity(cartDto);
            Cart cart = cartService.get(cartDto.getId());
            Set<CartRegistration> cartRegistrations = oldCart.getCartRegistrations();
            for (CartRegistration cartRegistration:
                    cartRegistrations) {
                BookRegistration registration = cartRegistration.getBookRegistration();
                Library library = registration.getLibrary();
                for (int i=0; i<cartRegistration.getCount(); i++){
                    library.returnBook(registration.getBook());
                }
                cart.removeCartRegistration(cartRegistration);
                libraryService.save(library);
            }
            if(cart.getCartRegistrations().isEmpty()) {
                cart.setStatus(Status.RETURNED);
            }
            cart.setId(cartDto.getId());
            cartService.save(cart);
            return new ResponseEntity<>("Books were returned to the library", HttpStatus.OK);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/listCarts", method = RequestMethod.GET)
    public List<CartDto> listCarts() {
        List<Cart> carts = cartService.listAll();
        return carts.stream().map(mapper::convertToDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/changeStatusCart/{id}_{status}", method = RequestMethod.PUT)
    public ResponseEntity<?> setCartStatus(@PathVariable Long id, @PathVariable Status status) {
        try {
            Cart cart = cartService.get(id);
            cart.setStatus(status);
            cartService.save(cart);
        }
        catch (RecordNotFoundException ex){
            throw new RecordNotFoundException("No such cart number");
        }
        return new ResponseEntity<>("Cart status was successfully changed", HttpStatus.OK);
    }

    @RequestMapping(value = "/addCartRegistration", method = RequestMethod.PUT)
    public ResponseEntity<?> addCartRegistration(@Valid @RequestBody CartRegistrationDto cartRegistrationDto) {
        try {
            CartRegistration cartRegistration = mapper.convertToEntity(cartRegistrationDto);
            if (cartRegistration.getCount() > cartRegistration.getBookRegistration().getCount()) {
                throw new RecordNotFoundException("invalid count of books");
            }
            BookRegistration registration = cartRegistration.getBookRegistration();
            Cart cart = cartRegistration.getCart();
            cart.addCartRegistration(cartRegistration);
            cartService.save(cart);
            cartRegistration = cart.getCartRegistrations().stream().reduce((prev, next) -> next).orElse(null);
            registration.addCartRegistration(cartRegistration);
            registrationService.save(registration);
            Library library = registration.getLibrary();
            for (int i = 0; i < cartRegistration.getCount(); i++) {
                library.takeBook(registration.getBook());
            }
            libraryService.save(registration.getLibrary());
            return new ResponseEntity<>("Books are in hold", HttpStatus.OK);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>("Wrong attributes", HttpStatus.CONFLICT);
        }
    }
}
