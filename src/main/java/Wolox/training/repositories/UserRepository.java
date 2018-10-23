package Wolox.training.repositories;

import Wolox.training.models.User;
import org.springframework.data.repository.*;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    List<User> findByBirthdayBetweenAndUsernameContainingAllIgnoreCase(LocalDate birthday1, LocalDate birthday2, String username);

}
