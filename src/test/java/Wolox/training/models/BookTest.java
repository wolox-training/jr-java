package Wolox.training.models;

import Wolox.training.repositories.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
public class BookTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @Before
    public void setUp() {
        // given
        book = new Book();
        book.setAuthor("author");
        book.setImage("image");
        book.setIsbn("isbn");
        book.setPublisher("publisher");
        book.setTitle("title");
        book.setSubtitle("subtitle");
        book.setPages(1);
        book.setGenre("genre");
        book.setYear("year");
        entityManager.persist(book);
        entityManager.flush();
    }

    @Test
    public void whenFindByAuthor_ReturnBook() {
        // when
        Book found = bookRepository.findByAuthor(book.getAuthor(), new PageRequest(0, 1, Sort.Direction.ASC, "title")).getContent().get(0);

        // then
        assertThat(found.getAuthor())
                .isEqualTo(book.getAuthor());
    }

    @Test
    public void whenFindByIsbn_ReturnBook() {
        // when
        Book found = bookRepository.findByIsbn("isbn").get();

        // then
        assertThat(found.getIsbn()).
                isEqualTo(book.getIsbn());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenFindByNonExistentIsbn_ReturnNull() {
        // when
        Book found = bookRepository.findByIsbn("non_existent").get();

        // then
        // exception is thrown
    }

    @Test(expected = PersistenceException.class)
    public void whenAddBookWithNullFields_Fail() {
        // when
        entityManager.persist(new Book());
        entityManager.flush();

        // then
        // an exception is thrown
    }

}
