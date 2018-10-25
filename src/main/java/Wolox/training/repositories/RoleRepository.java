package Wolox.training.repositories;

import Wolox.training.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer>  {

    Optional<Role> findByName(String name);

}
