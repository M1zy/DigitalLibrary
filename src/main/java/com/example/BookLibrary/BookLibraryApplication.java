package com.example.BookLibrary;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class BookLibraryApplication {

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(BookLibraryApplication.class, args);
        log.info("APPLICATION FINISHED");
    }

}
