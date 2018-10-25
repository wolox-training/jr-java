package Wolox.training.models;

import Wolox.training.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() {
        // given
        user = new User();
        user.setUsername("Polo");
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void whenFindByUsername_ReturnUser() {
        // when
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertThat(found.getUsername())
            .isEqualTo(user.getUsername());
    }

    @Test
    public void whenAddBookToLibrary_ReturnUpdatedList() {
        // when
        user.addBookToLibrary(new Book());
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertThat(found.getLibrary().size()).
                isEqualTo(1);
    }

    @Test(expected = PersistenceException.class)
    public void whenAddUserWithoutUsername_Fail() {
        // when
        entityManager.persist(new User());
        entityManager.flush();

        // then
        // an exception is thrown
    }

}
