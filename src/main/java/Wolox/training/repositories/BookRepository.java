package Wolox.training.repositories;

import Wolox.training.models.Book;
import org.springframework.data.repository.*;

public interface BookRepository extends CrudRepository<Book, Integer> {

    Book findByAuthor(String author);

}
