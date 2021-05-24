package com.example.BookLibrary.controller;

import com.example.BookLibrary.domains.*;
import com.example.BookLibrary.dto.BookDto;
import com.example.BookLibrary.dto.RegistrationDto;
import com.example.BookLibrary.exception.RecordNotFoundException;
import com.example.BookLibrary.ftp.FtpService;
import com.example.BookLibrary.mapper.Mapper;
import com.example.BookLibrary.service.*;
import com.example.BookLibrary.util.BookGenerator;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api(value="Books", description="Operations to books")
@RequiredArgsConstructor
@Log4j2
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private Mapper mapper;
    
    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private FtpService ftpService;

    @Autowired
    private FileService fileService;

    private static final int recordsPerPage = 4;

    @RequestMapping(value = "/{currentPage}",method = RequestMethod.GET)
    public ModelAndView home(HttpSession session, @PathVariable(required = false) Integer currentPage) {
        List<Author> authorList = authorService.listAll();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("mode", "Mode_home");
        session.setAttribute("listAuthors", authorList);
        session.setAttribute("listGenres", genreService.listAll());
        List<Book> listBooks = bookService.findBooks(currentPage,recordsPerPage);
        mav.addObject("listBook", listBooks);
        int nOfPages = bookService.listAll().size() / recordsPerPage;

        if (bookService.listAll().size() % recordsPerPage > 0) {
            nOfPages++;
        }

        session.setAttribute("nOfPages", nOfPages);
        session.setAttribute("currentPage", currentPage);
        return mav;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView home(HttpSession session) {
        if(session.getAttribute("category")==null) {
            session.setAttribute("category", true);
        }
        return home(session,1);
    }


    @RequestMapping(value = "/show/{id}", method= RequestMethod.GET)
    public ModelAndView getBook(@PathVariable Long id, HttpServletRequest request,HttpServletResponse response, HttpSession session){
        if(!bookService.exist(id)){
            throw new RecordNotFoundException("Invalid book id : " + id);
        }
        Book book = bookService.get(id);
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("book", book);
        mav.addObject("mode", "Mode_book");
        if(session.getAttribute("user")!=null) {
            User user = userService.get(((User) session.getAttribute("user")).getId());
            if(ratingService.isExistByUserAndBook(user,book)) {
                mav.addObject("userRating", ratingService.getRatingByUserAndBook(user,book).getRating());
            }
        }
        if(ratingService.getTotalRatingByBook(book)==null){
            mav.addObject("baseRate", "None");
        }
        else{
            mav.addObject("baseRate", ratingService.getTotalRatingByBook(book));
        }
        return mav;
    }

    @RequestMapping(value = "/addingRating/{id}", method = RequestMethod.POST )
    public RedirectView addingRating(@PathVariable Long id, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
        if(!bookService.exist(id)){
            throw new RecordNotFoundException("Invalid book id : " + id);
        }
        Book book = bookService.get(id);
        if(session.getAttribute("user")!=null) {
            User user = userService.get(((User) session.getAttribute("user")).getId());
            int rating = Integer.parseInt(request.getParameter("rating"));
            if(ratingService.isExistByUserAndBook(user,book)){
                Rating rate = ratingService.getRatingByUserAndBook(user,book);
                rate.setRating(rating);
                ratingService.save(rate);
            }
            else{
                ratingService.save(new Rating(book, user, rating));
            }
        }
        else{
            response.sendRedirect("/show/"+id);
            throw new RecordNotFoundException("You have to be authorized");
        }
        return new RedirectView("/show/"+id);
    }

    @RequestMapping(value = "/switchCategory", method = RequestMethod.GET)
    public RedirectView switchCategory(HttpSession session){
        session.setAttribute("category",!(boolean)session.getAttribute("category"));
        return new RedirectView("/");
    }

    @RequestMapping(value = "/search/{keyword}",method = RequestMethod.GET)
    public ModelAndView  filter(@PathVariable String keyword) {
        ModelAndView mav = new ModelAndView("index");
        List<Book> books = bookService.listByName(keyword);
        mav.addObject("listBook",books);
        mav.addObject("mode", "Mode_home");
        return mav;
    }

    @RequestMapping(value = "/searchByAuthor/{id}",method = RequestMethod.GET)
    public ModelAndView filterByAuthor(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("index");
        List<Book> books = bookService.listByAuthor(authorService.get(id));
        mav.addObject("listBook",books);
        mav.addObject("mode", "Mode_home");
        return mav;
    }

    @RequestMapping(value = "/searchByGenre/{id}",method = RequestMethod.GET)
    public ModelAndView filterByGenre(@PathVariable Long id,HttpSession session) {
        return filterByGenre(id,session,1);
    }

    @RequestMapping(value = "/searchByGenre/{id}/{currentPage}",method = RequestMethod.GET)
    public ModelAndView filterByGenre(@PathVariable Long id,HttpSession session,@PathVariable(required = false) Integer currentPage) {
        ModelAndView mav = home(session);
        List<Book> listBooks = bookService.findBooksByGenre(currentPage,recordsPerPage,genreService.get(id));
        mav.addObject("listBook", listBooks);
        int nOfPages = bookService.listByGenre(genreService.get(id)).size() / recordsPerPage;
        if (bookService.listByGenre(genreService.get(id)).size() % recordsPerPage > 0) {
            nOfPages++;
        }
        session.setAttribute("nOfPages", nOfPages);
        session.setAttribute("currentPage", currentPage);
        return mav;
    }

    @RequestMapping(value = "/Catalog/add", method = RequestMethod.GET)
    public ModelAndView getAddingBookPage(){
        ModelAndView mav = new ModelAndView("admin/Admin");
        List<Author> listAuthor = authorService.listAll();
        List<Genre> listGenre = genreService.listAll();
        mav.addObject("listAuthor", listAuthor);
        mav.addObject("listGenre", listGenre);
        mav.addObject("mode", "Mode_adminAddBook");
        return mav;
    }

    @RequestMapping(value = "/Catalog/{currentPage}", method = RequestMethod.GET)
    public ModelAndView getBooksPage(@PathVariable(required = false) Integer currentPage, HttpSession session,
                                     HttpServletResponse response) throws IOException {
        redirectToMainPage(response, session);
        ModelAndView mav = new ModelAndView("admin/Admin");
        mav.addObject("mode", "Mode_adminBooks");
        List<Book> listBooks = bookService.findBooks(currentPage,8);
        mav.addObject("listBook", listBooks);
        int nOfPages = bookService.listAll().size() / 8;

        if (bookService.listAll().size() % 8 > 0) {
            nOfPages++;
        }

        session.setAttribute("nOfPages", nOfPages);
        session.setAttribute("currentPage", currentPage);
        return mav;
    }

    @RequestMapping(value = "/Catalog/", method = RequestMethod.GET)
    public ModelAndView getBooksPage(HttpSession session, HttpServletResponse response) throws IOException {
        return getBooksPage(1,session, response);
    }

    @RequestMapping(value = "/Catalog/redactingBook/{id}", method = RequestMethod.GET)
    public ModelAndView getRedactingBookPage(@PathVariable Long id, HttpServletResponse response, HttpSession session) throws IOException {
        if(!bookService.exist(id)){
            throw new RecordNotFoundException("Invalid book id : " + id);
        }
        redirectToMainPage(response,session);
        List<Author> listAuthor = authorService.listAll();
        List<Genre> listGenre = genreService.listAll();
        Book book = bookService.get(id);
        ModelAndView mav = new ModelAndView("admin/Admin");
        mav.addObject("bookDto", mapper.convertToDto(book));
        mav.addObject("listAuthor", listAuthor);
        mav.addObject("listGenre", listGenre);
        mav.addObject("mode", "Mode_redactingBook");
        return mav;
    }

    public void addingBookToCatalogue(Book book, MultipartFile pdf, MultipartFile file, Long[] authors,Long[] genres){
        if(authors!=null) {
            Set<Long> authorsIds = new HashSet<>(Arrays.asList(authors));
            book.setAuthors(authorsIds.stream().map(x -> authorService.get(x)).collect(Collectors.toSet()));
        }
        else{
            book.setAuthors(new HashSet<>());
        }
        if(genres!=null) {
            Set<Long> genreIds = new HashSet<>(Arrays.asList(genres));
            book.setGenres(genreIds.stream().map(x -> genreService.get(x)).collect(Collectors.toSet()));
        }
        else{
            book.setGenres(new HashSet<>());
        }
        if (!file.isEmpty()) {
            book.setImage(file.getOriginalFilename());
            fileService.uploadFile(file);
        }
        else{
            book.setImage("default.png");
        }
        if (!pdf.isEmpty()) {
            book.setPdf(pdf.getOriginalFilename());
            fileService.uploadFile(pdf);
        }
        bookService.save(book);
    }

    @RequestMapping(value = "/addingBook", consumes = {"multipart/form-data"},method = RequestMethod.POST)
    public ModelAndView saveBook(@Valid @ModelAttribute BookDto bookDto, BindingResult result, @RequestParam(required = false) Long[] authors,
                                 @RequestParam(required = false) Long[] genres, @RequestParam MultipartFile file, @RequestParam MultipartFile pdf,
                                 HttpSession session, HttpServletResponse response) throws IOException {
        ModelAndView mav;
            mav = new ModelAndView("admin/Admin");
            redirectToMainPage(response,session);
        if (result.hasErrors()) {
            mav.addObject("mode","Mode_adminAddBook");
            mav.addObject("bookDto", bookDto);
            mav.addObject("listAuthor",authorService.listAll());
            mav.addObject("listGenre",genreService.listAll());
            mav.addObject("error",result.getAllErrors().get(0).getDefaultMessage());
            return mav;
        }
            try {
                Book book = mapper.convertToEntity(bookDto);
                addingBookToCatalogue(book,pdf,file,authors,genres);
                mav.addObject("mode", "Mode_adminHome");
            } catch (Exception exception) {
                mav.addObject("mode", "Mode_adminAddBook");
                mav.addObject("listAuthor",authorService.listAll());
                mav.addObject("listGenre",genreService.listAll());
                mav.addObject("bookDto",bookDto);
                mav.addObject("error", "invalid attributes");
            }
            return mav;
    }

    @RequestMapping(value = "/addingBookToArchive/{id}",method = RequestMethod.GET)
    public ModelAndView addBookToUserArchive(@PathVariable Long id, HttpSession session){
        if(bookService.exist(id)) {
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("mode", "Mode_book");
            Book book = bookService.get(id);
            mav.addObject("book", book);
            if(session.getAttribute("user")!=null){
                User user = userService.get(((User)session.getAttribute("user")).getId());
                book.addBookToArchive(user);
                bookService.save(book);
            } else {
                mav.addObject("error", "You have to be authorized");
            }
            return mav;
        }
        else {
            session.setAttribute("error","Wrong book id");
            return home(session);}
    }

    @RequestMapping(value="/Catalog/delete/{id}", method = RequestMethod.GET)
    public RedirectView delete(@PathVariable Long id, HttpSession session) throws IOException {
        try {
            if(session.getAttribute("admin")==null)
                return new RedirectView("/");
            bookService.delete(id);
            return new RedirectView("/Catalog/");
        }
        catch (Exception e){
            throw new RecordNotFoundException("Invalid book id : " + id);
        }
    }

    public void redirectToMainPage(HttpServletResponse response,HttpSession session) throws IOException {
        if(session.getAttribute("admin")==null) {
            response.sendRedirect("/");
        }
    }

    @RequestMapping(value = "/Catalog/update/{id}",consumes = {"multipart/form-data"}, method = RequestMethod.POST)
    public ModelAndView updateBook(@Valid @ModelAttribute BookDto bookDto,BindingResult result, @RequestParam(required = false) Long[] authors,
                                   @RequestParam(required = false) Long[] genres, @RequestParam MultipartFile file,
                                   @RequestParam MultipartFile pdf, @PathVariable Long id, HttpServletResponse response, HttpSession session) throws IOException {
        redirectToMainPage(response,session);
        ModelAndView mav = new ModelAndView("admin/Admin");
        if(result.hasErrors()){
            mav.addObject("mode","Mode_redactingBook");
            mav.addObject("listAuthor", authorService.listAll());
            mav.addObject("book", bookService.get(id));
            mav.addObject("listGenre", genreService.listAll());
            mav.addObject("error",result.getAllErrors().get(0).getDefaultMessage());
            return mav;
        }
        if(!bookService.exist(id)){
            throw new RecordNotFoundException("Invalid book id : " + id);
        }
        else {
            try {
                Book book = bookService.get(id);
                book.setDescription(bookDto.getDescription());
                book.setPages(bookDto.getPages());
                book.setYear(bookDto.getYear());
                book.setName(bookDto.getName());
                addingBookToCatalogue(book,pdf,file,authors,genres);
                mav.addObject("mode", "Mode_adminHome");
            }
            catch (Exception ex){
                mav.addObject("mode", "Mode_redactingBook");
                mav.addObject("listAuthor", authorService.listAll());
                mav.addObject("book", bookService.get(id));
                mav.addObject("listGenre", genreService.listAll());
                mav.addObject("error", "invalid attributes");
            }
        }
        return mav;
    }

    @RequestMapping(value = "/addRegistration", method = RequestMethod.PUT)
    public ResponseEntity<?> addingLibrary(@Valid @RequestBody RegistrationDto registrationDto) {
        try {
            BookRegistration bookRegistration = mapper.convertToEntity(registrationDto);
            Book book = bookService.get(bookRegistration.getBook().getId());
            book.addRegistration(bookRegistration);
            bookService.save(book);
            return new ResponseEntity<>("Book added to library successfully", HttpStatus.OK);
        }
        catch (Exception e){
            throw new RecordNotFoundException("Invalid book or library ids;");
        }
    }

    @RequestMapping(value = "/toFile/{id}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> excelBookReport(@PathVariable Long id) throws IOException, IOException {
        if(!bookService.exist(id)){
            throw new RecordNotFoundException("Invalid book id : " +id);
        }
        Book book = bookService.get(id);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-disposition", "attachment;filename=book.xlsx");
        BookGenerator bookGenerator = new BookGenerator();
        ByteArrayInputStream in = bookGenerator.toExcel(book);
        in.close();
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

    @RequestMapping(value = "/listOfBooks", method = RequestMethod.GET)
    public List<String> listFiles() throws IOException {
        return ftpService.listFilenames("");
    }

    @RequestMapping(value="/upload/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@PathVariable Long id,@RequestPart(required = true) MultipartFile file) {
        try {
            Book book = bookService.get(id);
            book.setPath(file.getOriginalFilename());
            ftpService.uploadFile(file);
            bookService.save(book);
            return new ResponseEntity<>("Book report was uploaded successfully", HttpStatus.OK);
        }
        catch (Exception e){
            throw new RecordNotFoundException("Invalid book id : " + id);
        }
    }

    @RequestMapping(value="/download/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> download(@PathVariable Long id) {
        try {
            Book book = bookService.get(id);
            ftpService.downloadFile(book);
            return new ResponseEntity<>("Book report was downloaded successfully", HttpStatus.OK);
        }
        catch (Exception e){
            throw new RecordNotFoundException(bookService.get(id).getPath());
        }
    }


}
