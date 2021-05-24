package com.example.BookLibrary.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.BookLibrary.domains.ErrorResponse;
import com.example.BookLibrary.exception.RecordNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
@Log4j2
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ModelAndView handleAllExceptions(Exception ex) {
        ModelAndView mav = new ModelAndView("index");
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server Error", details);
        log.error(error);
        mav.addObject("details",details);
        mav.addObject("error","Server Error");
        mav.addObject("mode", "Mode_error");
        return mav;
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ModelAndView handleRecordNotFoundException(RecordNotFoundException ex) {
        ModelAndView mav = new ModelAndView("index");
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Record Not Found", details);
        log.error(error);
        mav.addObject("details",details);
        mav.addObject("error","Record Not Found");
        mav.addObject("mode", "Mode_error");
        return mav;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        log.error(error);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}