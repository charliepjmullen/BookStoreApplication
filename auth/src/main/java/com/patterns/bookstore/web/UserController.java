package com.patterns.bookstore.web;

import com.patterns.bookstore.model.Book; 
import com.patterns.bookstore.model.User;
import com.patterns.bookstore.repository.BookRepository;
import com.patterns.bookstore.repository.UserRepository;
import com.patterns.bookstore.service.BookService;
import com.patterns.bookstore.service.SecurityService;
import com.patterns.bookstore.service.UserService;
import com.patterns.bookstore.validator.UserValidator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    


    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired 
    private UserRepository userRepository;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome() {
    	

        return "welcome";
    }
    
    @RequestMapping(value = "viewallbooks", method = RequestMethod.GET)
    public String bookList(@Valid Book book, Model model) {
    	List<Book> booksList = new ArrayList<>();
    	booksList = bookRepository.findAll();
    	
    	model.addAttribute("booksList", booksList);
    	
    	return "bookList";
    }
    
    
    @RequestMapping(value = "/addBook", method = RequestMethod.GET)
    public String addBook(@Valid Book book, Model model, BindingResult result) {
    	

        return "addbook";
    }
    
   /* @RequestMapping(value = "/shoppingCart/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public String shoppingCart(@Valid Book book, Model model, @RequestParam("id") Long id) {
    	
    	  System.out.println(id);
          book = bookRepository.findOne(id);
          System.out.println("Book: " + book + "Title:" + id);

          Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String username = loggedInUser.getName(); // Authentication for 
	      User user = userRepository.findByUsername(username);
	      
	      user.saveBookToShoppingCart(book);
	      userRepository.save(user);
	      
	      System.out.println(user + " just saved " + book.getTitle() + " to their shopping cart");
	      
	      
        return "bookList";
    }
    */
    
 /*   @RequestMapping(value = "/shoppingCart", method = RequestMethod.POST)
    public @ResponseBody String shoppingCart(Book book, @RequestParam("id") Long id) {
    	
    	  System.out.println(id);
          book = bookRepository.findOne(id);
          System.out.println("Book: " + book + "Title:" + id);

          Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String username = loggedInUser.getName(); // Authentication for 
	      User user = userRepository.findByUsername(username);
	      
	      user.saveBookToShoppingCart(book);
	      userRepository.save(user);
	      
	      System.out.println(user + " just saved " + book.getTitle() + " to their shopping cart");
	      
	      
        return "bookList";
    }*/
    
    @RequestMapping(value = " /shoppingcart/{bookId}", method=RequestMethod.GET)
    public String shoppingCart(@PathVariable("bookId")Long id, Model model) {

      // This gets the currently logged in user
      Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
      String username = loggedInUser.getName(); // Authentication for 
      User user = userRepository.findByUsername(username);

      
      //Retrieve book
      Book book = bookRepository.findById(id);
      String title = book.getTitle();
      
      System.out.println(username + "saved" + "Book: " + title);

      // This will add the book to the user's booklist
      user.saveBookToShoppingCart(book);
      userRepository.save(user);

      return "shoppingcart";
    }
    
    /*@RequestMapping(value = "/viewShoppingCart")
    */
    
    
}
