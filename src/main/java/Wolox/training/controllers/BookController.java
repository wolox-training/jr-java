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

    @GetMapping(value = {"/", ""})
    public String home() {
        return "home";
    }

    // Create
    @RequestMapping("/create")
    @PostMapping("/books/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Read
    @GetMapping("/view")
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/view/{id}")
    public Book findById(@PathVariable int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            throw new RuntimeException("The book does not exist");
        }
        return book.get();
    }

    // Update methods
    @PutMapping("/view/setAuthor/{id}")
    public Book updateAuthor(@RequestParam (name = "author") String author, @PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setGenre/{id}")
    public Book updateGenre(@RequestParam (name = "genre") String genre, @PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        book.setGenre(genre);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setImage/{id}")
    public Book updateImage(@RequestParam (name = "image") String image, @PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        book.setImage(image);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setIsbn/{id}")
    public Book updateIsbn(@RequestParam (name = "isbn") String isbn, @PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setPages/{id}")
    public Book updatePages(@RequestParam (name = "pages") int pages, @PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        book.setPages(pages);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setPublisher/{id}")
    public Book updatePublisher(@RequestParam (name = "publisher") String publisher, @PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setSubtitle/{id}")
    public Book updateSubtitle(@RequestParam (name = "subtitle") String subtitle, @PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        book.setSubtitle(subtitle);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setTitle/{id}")
    public Book updateTitle(@RequestParam (name = "title") String title, @PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        book.setTitle(title);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setYear/{id}")
    public Book updateYear(@RequestParam (name = "year") String year, @PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("The book does not exist"));
        book.setYear(year);
        return bookRepository.save(book);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bookRepository.deleteById(id);
    }

}
