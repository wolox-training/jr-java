package Wolox.training.repositories;

import Wolox.training.models.Book;
import org.springframework.data.repository.*;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Integer> {

    Optional<Book> findByAuthor(String author);

    Optional<Book> findByIsbn(String isbn);

}
