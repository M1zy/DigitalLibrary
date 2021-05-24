package com.example.BookLibrary.service;

import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Rating;
import com.example.BookLibrary.domains.User;
import com.example.BookLibrary.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    public void save(Rating rating){
        ratingRepository.save(rating);
    }

    public boolean isExistByUserAndBook(User user, Book book){
        return ratingRepository.getByUserAndBook(user, book) != null;
    }

    public Rating getRatingByUserAndBook(User user, Book book){
        return ratingRepository.getByUserAndBook(user,book);
    }

    public List<Rating> getAllByBook(Book book){
        return ratingRepository.getAllByBook(book);
    }

    public Double getTotalRatingByBook(Book book){
        List <Rating> ratingList = getAllByBook(book);
        if(ratingList.isEmpty())return null;
        double baseRate = 0;
        for (Rating rating:
                ratingList) {
            baseRate += rating.getRating();
        }
        baseRate /= ratingList.size();
        return baseRate;
    }
}
