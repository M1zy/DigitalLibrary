package com.example.BookLibrary.repository;

import com.example.BookLibrary.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
 boolean existsUserByEmailAndAndPassword(String email, String password);
 User getByEmailAndPassword(String email, String password);
}
