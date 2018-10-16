package Wolox.training.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

@Entity
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column
    private String name;

    @Column
    private LocalDate birthday;

    @Column
    @ManyToMany(cascade = {CascadeType.ALL})
    private Collection<Book> books = new LinkedList<Book>();

    public User() {
    }

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

    public boolean hasBook(Book book) {
        boolean hasBook = false;
        Iterator<Book> it = this.books.iterator();
        Book currentBook;
        while (it.hasNext() && !hasBook) {
            currentBook = it.next();
            hasBook = (currentBook.getId() == book.getId()) ||
                    (currentBook.getTitle() == book.getTitle() &&
                    currentBook.getAuthor() == book.getAuthor() &&
                    currentBook.getPublisher() == book.getPublisher() &&
                    currentBook.getIsbn() == book.getIsbn());
        }
        return hasBook;
    }

    public int getId() {
        return this.id;
    }
}
