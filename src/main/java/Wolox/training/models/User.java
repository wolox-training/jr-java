package Wolox.training.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String username;

    @Column
    private String name;

    @Column
    private LocalDate birthday;

    @Column
    @ManyToMany
    private Collection<Book> books = new LinkedList<Book>();

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Collection<Book> getLibrary() {
        return this.books;
    }

    public void addBookToLibrary(Book book) {
        this.books.add(book);
    }

    public void removeBookFromLibrary(Book book) {
        this.books.remove(book);
    }
}
