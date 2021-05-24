package com.example.BookLibrary.repository;

import com.example.BookLibrary.domains.CartRegistration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRegistrationRepository extends CrudRepository<CartRegistration, Long> {
}
