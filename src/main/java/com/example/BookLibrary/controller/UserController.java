package com.example.BookLibrary.controller;

import com.example.BookLibrary.domains.Book;
import com.example.BookLibrary.domains.Library;
import com.example.BookLibrary.domains.User;
import com.example.BookLibrary.dto.UserDto;
import com.example.BookLibrary.exception.RecordNotFoundException;
import com.example.BookLibrary.mapper.Mapper;
import com.example.BookLibrary.recaptcha.VerifyRecaptcha;
import com.example.BookLibrary.service.BookService;
import com.example.BookLibrary.service.LibraryService;
import com.example.BookLibrary.service.UserService;
import com.example.BookLibrary.util.UserGenerator;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Api(value="Users", description="Operations to users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private Mapper mapper = new Mapper();


    @RequestMapping(value = "/list", method= RequestMethod.GET)
    public List<UserDto> list() {
        List<User> users = userService.listAll();
        return users.stream().map(mapper::convertToDto).collect(Collectors.toList());
    }

    public void redirectToMainPage(HttpServletResponse response,HttpSession session) throws IOException {
        if(session.getAttribute("admin")==null) {
            response.sendRedirect("/");
        }
    }

    @RequestMapping(value = "/show/{id}", method= RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        if(!userService.exist(id)){
            throw new RecordNotFoundException("Invalid user id : "+id);
        }
        User user = userService.get(id);
        return new ResponseEntity<>(mapper.convertToDto(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public ModelAndView saveUser(@Valid @ModelAttribute UserDto userDto, BindingResult result, HttpServletResponse response,
                                 HttpServletRequest request, @RequestParam String conpassword) throws ParseException {
        ModelAndView mav = new ModelAndView("index");
        if (result.hasErrors()) {
            mav.addObject("mode","Mode_registration");
            mav.addObject("userDtp", userDto);
            request.setAttribute("error",result.getAllErrors().get(0).getDefaultMessage());
            return mav;
        }
        try {
            User user = mapper.convertToEntity(userDto);
            if(conpassword.equals(user.getPassword())) {
                user.setAuthority(false);
                userService.save(user);
                response.sendRedirect("/1");
            }
            else{
                mav.addObject("mode","Mode_registration");
                request.setAttribute("error","password mismatch");
            }
        }
        catch (Exception ex){
            mav.addObject("mode","Mode_registration");
            request.setAttribute("error","something went wrong");
            log.error(ex.getMessage());
        }
        return mav;
    }

    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    public ModelAndView loginUser(@ModelAttribute UserDto userDto, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) throws ParseException, IOException {
        ModelAndView mav = new ModelAndView("index");
        String gRecaptchaResponse = request
                .getParameter("g-recaptcha-response");
        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        if(userService.existByEmailAndPassword(userDto.getEmail(),userDto.getPassword())&&verify){
            mav.addObject("mode", "Mode_user");
            session.setAttribute("user",userService.getUserByEmailAndPassword(userDto.getEmail(),userDto.getPassword()));
            response.sendRedirect("account");
        }
        else{
            mav.addObject("mode","Mode_login");
            request.setAttribute("error","Please check captcha or invalid email or password");
            System.out.println(gRecaptchaResponse);
        }
        return mav;
    }

    @RequestMapping(value = "/loginAdmin", method = RequestMethod.POST)
    public ModelAndView loginAdmin(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws ParseException, IOException {
        ModelAndView mav;
        if(userService.existByEmailAndPassword(userDto.getEmail(),userDto.getPassword())&&userService.getUserByEmailAndPassword(userDto.getEmail(),userDto.getPassword()).getAuthority()){
            mav = new ModelAndView("admin/Admin");
            session.setAttribute("admin", "admin");
            response.sendRedirect("AdminPage");
        }
        else{
            mav = new ModelAndView("admin/AdminLogin");
            request.setAttribute("error","Invalid admin email or password");
        }
        return mav;
    }

    @RequestMapping(value = "/AdminPage", method = RequestMethod.GET)
    public ModelAndView loginAdmin(HttpSession session){
        if(session.getAttribute("admin")!=null){
            ModelAndView mav = new ModelAndView("admin/Admin");
            mav.addObject("mode", "Mode_adminHome");
            return mav;}
        return new ModelAndView("admin/AdminLogin");
    }

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ModelAndView getAllUsersPage(HttpServletResponse response, HttpSession session) throws IOException {
        redirectToMainPage(response,session);
        List<User> userList = userService.listAll();
        ModelAndView mav = new ModelAndView("admin/Admin");
        mav.addObject("listUser", userList);
        mav.addObject("mode", "Mode_allUsers");
        return mav;
    }

    @RequestMapping(value = "/allUsers/newUser", method = RequestMethod.GET)
    public ModelAndView getNewUserPage(HttpServletResponse response, HttpSession session) throws IOException {
        redirectToMainPage(response,session);
        ModelAndView mav = new ModelAndView("admin/Admin");
        mav.addObject("mode", "Mode_newUser");
        return mav;
    }

    @RequestMapping(value = "/allUsers/edit", method = RequestMethod.GET)
    public ModelAndView getEditUserPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        redirectToMainPage(response,session);
        long id = Integer.parseInt(request.getParameter("id"));
        ModelAndView mav = new ModelAndView("admin/Admin");
        mav.addObject("mode", "Mode_newUser");
        mav.addObject("userDto", mapper.convertToDto(userService.get(id)));
        return mav;
    }


    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ModelAndView accountPage(HttpSession session){
        ModelAndView mav = new ModelAndView("index");
        if(session.getAttribute("user")!=null){
            mav.addObject("mode", "Mode_user");
            return mav;}
        mav.addObject("mode","Mode_login");
        return mav;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("mode", "Mode_registration");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("mode", "Mode_login");
        return mav;
    }

    @RequestMapping(value = "/logoutUser", method = RequestMethod.GET)
    public RedirectView logout(HttpSession session){
        session.setAttribute("user",null);
        session.setAttribute("admin",null);
        return new RedirectView("/");
    }

    @RequestMapping(value="/allUsers/delete", method = RequestMethod.GET)
    public RedirectView deleteUser(HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException {
        redirectToMainPage(response,session);
        long id = Integer.parseInt(request.getParameter("id"));
        try {
            userService.delete(id);
            return new RedirectView("/user/allUsers");
        }
        catch (Exception e){
            throw new RecordNotFoundException("Invalid user id : "+id);
        }
    }

    @RequestMapping(value = "/allUsers/update", method = RequestMethod.POST)
    public ModelAndView updateUser(@Valid @ModelAttribute UserDto userDto, BindingResult result,
                                   HttpSession session, HttpServletResponse response) throws IOException {
        redirectToMainPage(response,session);
        if (result.hasErrors()) {
            ModelAndView mav = getNewUserPage(response,session);
            mav.addObject("userDto", userDto);
            mav.addObject("error", result.getAllErrors().get(0).getDefaultMessage());
            return mav;
        }
        if(!userService.exist(userDto.getId())){
            throw new RecordNotFoundException("Invalid user id : " + userDto.getId());}
        User newUser = mapper.convertToEntity(userDto);
        newUser.setAuthority(userService.get(newUser.getId()).getAuthority());
        userService.save(newUser);
        response.sendRedirect("/user/allUsers");
        return getAllUsersPage(response,session);
    }

    @RequestMapping(value = "/allUsers/saveUser", method = RequestMethod.POST)
    public ModelAndView saveUser(@Valid @ModelAttribute UserDto userDto,BindingResult result,
                                 HttpSession session, HttpServletResponse response) throws IOException {
        redirectToMainPage(response,session);
        if (result.hasErrors()) {
            ModelAndView mav = getNewUserPage(response,session);
            mav.addObject("userDtp", userDto);
            mav.addObject("error",result.getAllErrors().get(0).getDefaultMessage());
            return mav;
        }
        User newUser = mapper.convertToEntity(userDto);
        userService.save(newUser);
        response.sendRedirect("/user/allUsers");
        return getAllUsersPage(response,session);
    }

    @RequestMapping(value = "/archive/{currentPage}", method = RequestMethod.GET)
    public ModelAndView archive(@PathVariable(required = false) Integer currentPage,HttpSession session){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("mode", "Mode_archive");
        User user = (User) session.getAttribute("user");
        mav.addObject("listBook", user.getBooksArchive());
        int nOfPages = user.getBooksArchive().size() / 8;

        if (nOfPages % 8 > 0) {
            nOfPages++;
        }

        session.setAttribute("nOfPages", nOfPages);
        session.setAttribute("currentPage", currentPage);
        return mav;
    }

    @RequestMapping(value = "/archive", method = RequestMethod.GET)
    public ModelAndView archive(HttpSession session){
        return archive(1,session);
    }

    public boolean isLibraryContainingBooks(Library library, Set<Book> books){
        return (libraryService.bookSet(library.getId()).containsAll(books));
    }

    @RequestMapping(value = "/toFile/{id}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> excelUserReport(@PathVariable Long id) throws IOException {
        if(!userService.exist(id)){
            throw new RecordNotFoundException("Invalid user id : " + id);
        }
        User user = userService.get(id);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-disposition", "attachment;filename=user.xlsx");
        UserGenerator userGenerator = new UserGenerator();
        ByteArrayInputStream in = userGenerator.toExcel(user);
        in.close();
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }
}