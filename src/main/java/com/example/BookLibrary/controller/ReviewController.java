package com.example.BookLibrary.controller;

import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Review;
import com.example.BookLibrary.domains.User;
import com.example.BookLibrary.dto.AuthorDto;
import com.example.BookLibrary.dto.ReviewDto;
import com.example.BookLibrary.exception.RecordNotFoundException;
import com.example.BookLibrary.mapper.Mapper;
import com.example.BookLibrary.service.BookService;
import com.example.BookLibrary.service.LibraryService;
import com.example.BookLibrary.service.ReviewService;
import com.example.BookLibrary.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@Api(value="Books", description="Operations to books")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private Mapper mapper = new Mapper();

    @RequestMapping(value = "/show/{id}", method= RequestMethod.GET)
    public ModelAndView getBookReviews(@PathVariable Long id, HttpSession session){
        if(!bookService.exist(id)){
            throw new RecordNotFoundException("Invalid book id : " + id);
        }
        Book book = bookService.get(id);
        ModelAndView mav = new ModelAndView("index");
        List<Review> reviews = reviewService.getAllReviewsByBook(book);
        mav.addObject("listReview", reviews);
        session.setAttribute("bookid",id);
        mav.addObject("mode", "Mode_reviews");
        return mav;
    }

    @RequestMapping(value = "/addBookReview", method=RequestMethod.POST)
    public ModelAndView addBookReview(String text, HttpServletRequest request, HttpSession session){
            if (session.getAttribute("user") != null) {
                User user = (User) session.getAttribute("user");
                ReviewDto reviewDto = new ReviewDto(text,user.getId(),(Long)session.getAttribute("bookid"));
                Review review = mapper.convertToEntity(reviewDto);
                reviewService.save(review);
            } else {
                request.setAttribute("error", "You have to authorize");
                System.out.println("Error");
            }
        Book book = bookService.get((Long)session.getAttribute("bookid"));
        ModelAndView mav = new ModelAndView("index");
        List<Review> reviews = reviewService.getAllReviewsByBook(book);
        mav.addObject("listReview", reviews);
        mav.addObject("mode", "Mode_reviews");
        return mav;
    }
}
