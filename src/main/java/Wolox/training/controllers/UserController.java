package Wolox.training.controllers;

import Wolox.training.DAO.UserDAO;
import Wolox.training.exceptions.*;
import Wolox.training.models.Book;
import Wolox.training.models.User;
import Wolox.training.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public UserController() {
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Create
    @RequestMapping("/create/**")
    @PostMapping("/users/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Read
    @GetMapping("/view")
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/view/{id}")
    public User findById(@PathVariable int id) throws UserDoesNotExistException {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
    }

    @GetMapping("/view/{id}/library")
    public Iterable viewLibrary(@PathVariable int id) throws UserDoesNotExistException {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"))
                .getLibrary();
    }

    // Update methods
    @PutMapping("/view/setName/{id}")
    public User updateName(@RequestParam (name = "name") String name, @PathVariable int id) throws UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        user.setName(name);
        return userRepository.save(user);
    }

    @PutMapping("/view/setUsername/{id}")
    public User updateUsername(@RequestParam (name = "username") String username, @PathVariable int id) throws UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        user.setUsername(username);
        return userRepository.save(user);
    }

    @PutMapping("/view/setBirthday/{id}")
    public User updateBirthday(@RequestParam (name = "birthday") LocalDate birthday, @PathVariable int id) throws UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        user.setBirthday(birthday);
        return userRepository.save(user);
    }

    // Delete
    @DeleteMapping("/view/{id}")
    public void delete(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/addBook/{id}")
    public void addBookToLibrary(@PathVariable int id, @RequestBody Book book) throws BookAlreadyOwnedException, UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        if (user.hasBook(book)) {
            throw new BookAlreadyOwnedException("The user already has this book in their library");
        }
        user.addBookToLibrary(book);
        userRepository.save(user);
    }

    @PutMapping("/removeBook/{id}")
    public void removeBookFromLibrary(@PathVariable int id, @RequestBody Book book) throws UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        user.removeBookFromLibrary(book);
    }

}
