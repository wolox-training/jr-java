package Wolox.training.models;

import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
public class Role {

    private static final String WARNING_EMPTY = "Please provide a non empty field";
    private static final String WARNING_NULL = "Please provide a non null field";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))

    private Collection<Privilege> privileges;

    public Role() {

    }

    public Role(String name) {
        Preconditions.checkNotEmpty(name, WARNING_EMPTY);
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
    }

    public void removePrivilege(Privilege privilege) {
        this.privileges.remove(privilege);
    }

}
