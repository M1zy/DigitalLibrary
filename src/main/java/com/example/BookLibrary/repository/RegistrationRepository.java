package com.example.BookLibrary.repository;

import com.example.BookLibrary.domains.BookRegistration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends CrudRepository<BookRegistration, Long> {
}
