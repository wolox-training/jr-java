package Wolox.training.controllers;

import Wolox.training.exceptions.BookAlreadyOwnedException;
import Wolox.training.models.Book;
import Wolox.training.models.User;
import Wolox.training.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

//@Controller
@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Create
    @PostMapping("/create")
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
    public User findById(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("The user does not exist"));
    }

    // Update methods
    @PutMapping("/view/setName/{id}")
    public User updateName(@RequestParam (name = "name") String name, @PathVariable int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        user.setName(name);
        return userRepository.save(user);
    }

    @PutMapping("/view/setUsername/{id}")
    public User updateUsername(@RequestParam (name = "username") String username, @PathVariable int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        user.setUsername(username);
        return userRepository.save(user);
    }

    @PutMapping("/view/setBirthday/{id}")
    public User updateBirthday(@RequestParam (name = "birthday") LocalDate birthday, @PathVariable int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        user.setBirthday(birthday);
        return userRepository.save(user);
    }

    // Delete
    @DeleteMapping("/view/{id}")
    public void delete(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/addBook/{id}")
    public void addBookToLibrary(@PathVariable int id, @RequestBody Book book) throws BookAlreadyOwnedException {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("The user does not exist"));
        if (user.hasBook(book)) {
            throw new BookAlreadyOwnedException("The user already has this book in their library");
        }
        user.addBookToLibrary(book);
        userRepository.save(user);
    }

    @PutMapping("/removeBook/{id}")
    public void removeBookFromLibrary(@PathVariable int id, @RequestBody Book book) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("The user does not exist"));
        user.removeBookFromLibrary(book);
    }

}
