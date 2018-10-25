package Wolox.training.repositories;

import Wolox.training.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.*;

import java.time.LocalDate;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);

    Page<User> findByBirthdayBetweenAndUsernameContainingAllIgnoreCase(LocalDate birthday1, LocalDate birthday2,
                                                                       String username, Pageable pageable);

}
