package com.example.BookLibrary.service;

import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Cart;
import com.example.BookLibrary.domains.Review;
import com.example.BookLibrary.repository.CartRepository;
import com.example.BookLibrary.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public List<Review> listAll() {
        return (List<Review>) reviewRepository.findAll();
    }

    public Review get(Long id){
        return reviewRepository.findById(id).get();
    }

    public void save(Review review){
        reviewRepository.save(review);
    }

    public void delete(Review cart){
        reviewRepository.delete(cart);
    }

    public boolean exist(Long id){return reviewRepository.existsById(id);}

    public List<Review> getAllReviewsByBook(Book book){return reviewRepository.getAllByBook(book);}
}
