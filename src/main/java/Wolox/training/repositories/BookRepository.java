package Wolox.training.repositories;

import Wolox.training.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.*;

import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByAuthor(String author, Pageable pageable);

    Optional<Book> findByIsbn(String isbn);

    Page<Book> findByPublisherAndGenreAndYearAllIgnoreCase(String publisher, String genre, String year,
                                                           Pageable pageable);

}
