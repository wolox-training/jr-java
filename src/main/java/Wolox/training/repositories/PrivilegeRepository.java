package Wolox.training.repositories;

import Wolox.training.models.Privilege;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {

    public Optional<Privilege> findByName(String name);
}
