package Wolox.training.models;

import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {

    private static final String WARNING_EMPTY = "Please provide a non empty field";
    private static final String WARNING_NULL = "Please provide a non null field";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Preconditions.checkNotNull(id, WARNING_NULL);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkNotEmpty(name, WARNING_EMPTY);
        this.name = name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}