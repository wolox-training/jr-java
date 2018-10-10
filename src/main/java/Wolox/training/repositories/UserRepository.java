package Wolox.training.repositories;

import Wolox.training.models.User;
import org.springframework.data.repository.*;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
