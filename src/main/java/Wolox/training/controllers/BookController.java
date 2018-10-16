package Wolox.training.controllers;

import Wolox.training.DAO.BookDAO;
import Wolox.training.exceptions.BookDoesNotExistException;
import Wolox.training.models.Book;
import Wolox.training.repositories.BookRepository;
import Wolox.training.services.OpenLibraryService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

//@Controller
@RequestMapping("/api/books")
@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    private OpenLibraryService onlineLibrary = new OpenLibraryService();

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
    public Book create(@RequestBody Book book ) {
        return bookRepository.save(book);
    }

    // Read
    @GetMapping("/view")
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/view/{id}")
    public Book findById(@PathVariable int id) throws BookDoesNotExistException {
        return bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Book findLocalByIsbn(String isbn) throws BookDoesNotExistException {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book findInOnlineLibrary(String isbn) throws BookDoesNotExistException {
        try {
            BookDAO bookDAO = onlineLibrary.bookInfo(isbn);
            Book book = new Book(bookDAO);
            return this.create(book);
        } catch (RuntimeException e) {
            throw new BookDoesNotExistException("The book does not exist in the online library");
        }
    }

    @RequestMapping(value = "/view/isbn/{isbn}", method = { RequestMethod.GET, RequestMethod.POST })
    public Book findByIsbn(HttpServletResponse response, @PathVariable String isbn) {
        try {
            response.setStatus(HttpStatus.OK.value());
            return findLocalByIsbn(isbn);
        } catch (BookDoesNotExistException notInLocalLib) {
            try {
                response.setStatus(HttpStatus.CREATED.value());
                return findInOnlineLibrary(isbn);
            } catch (BookDoesNotExistException notInOnlineLib) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
        }
    }

    // Update methods
    @PutMapping("/view/setAuthor/{id}")
    public Book updateAuthor(@RequestParam (name = "author") String author, @PathVariable int id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setGenre/{id}")
    public Book updateGenre(@RequestParam (name = "genre") String genre, @PathVariable int id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
        book.setGenre(genre);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setImage/{id}")
    public Book updateImage(@RequestParam (name = "image") String image, @PathVariable int id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
        book.setImage(image);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setIsbn/{id}")
    public Book updateIsbn(@RequestParam (name = "isbn") String isbn, @PathVariable int id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setPages/{id}")
    public Book updatePages(@RequestParam (name = "pages") int pages, @PathVariable int id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
        book.setPages(pages);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setPublisher/{id}")
    public Book updatePublisher(@RequestParam (name = "publisher") String publisher, @PathVariable int id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setSubtitle/{id}")
    public Book updateSubtitle(@RequestParam (name = "subtitle") String subtitle, @PathVariable int id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
        book.setSubtitle(subtitle);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setTitle/{id}")
    public Book updateTitle(@RequestParam (name = "title") String title, @PathVariable int id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
        book.setTitle(title);
        return bookRepository.save(book);
    }

    @PutMapping("/view/setYear/{id}")
    public Book updateYear(@RequestParam (name = "year") String year, @PathVariable int id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The book does not exist"));
        book.setYear(year);
        return bookRepository.save(book);
    }

    // Delete
    @DeleteMapping("/view/{id}")
    public void delete(@PathVariable int id) {
        bookRepository.deleteById(id);
    }
}