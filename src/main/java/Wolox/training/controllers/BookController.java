package Wolox.training.controllers;

import java.util.Optional;
import Wolox.training.models.Book;
import Wolox.training.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return (Book)bookRepository.save(book);
    }

    // Read
    @GetMapping
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional findById(@PathVariable int id) {
        return bookRepository.findById(id);
    }

    // Update
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id) {
        Book book = bookRepository.findById(id).get();
        return bookRepository.save(book);
    }

    // Delete

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bookRepository.deleteById(id);
    }

}