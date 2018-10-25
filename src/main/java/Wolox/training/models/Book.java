package Wolox.training.models;

import Wolox.training.DAO.BookDAO;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;

import javax.persistence.*;

@Entity
public class Book {

    private static final String WARNING_EMPTY = "Please provide a non empty field";
    private static final String WARNING_LOWER_THAN_ZERO = "Please provide a number higher than zero";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private int pages;

    @Column(nullable = false)
    private String isbn;

    public Book() {

    }

    public Book(BookDAO bookDAO) {
        this.setTitle(bookDAO.getTitle());
        this.setSubtitle(bookDAO.getSubtitle());
        this.setPublisher(bookDAO.getPublishers());
        this.setIsbn(bookDAO.getIsbn());
        this.setYear(bookDAO.getPublishDate());
        this.setAuthor(bookDAO.getAuthors().stream().findFirst().get());
        this.setImage(bookDAO.getCover());
    }

    public void setGenre(String genre) {
        this.genre = Preconditions.checkNotEmpty(genre, WARNING_EMPTY);
    }

    public String getGenre() {
        return this.genre;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotEmpty(author, WARNING_EMPTY);
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setImage(String image) {
        Preconditions.checkNotEmpty(image, WARNING_EMPTY);
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setTitle(String title) {
        Preconditions.checkNotEmpty(title, WARNING_EMPTY);
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotEmpty(subtitle, WARNING_EMPTY);
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotEmpty(publisher, WARNING_EMPTY);
        this.publisher = publisher;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setYear(String year) {
        Preconditions.checkNotEmpty(year, WARNING_EMPTY);
        this.year = year;
    }

    public String getYear() {
        return this.year;
    }

    public void setPages(int pages) {
        Preconditions.checkArgument(pages > 0, WARNING_LOWER_THAN_ZERO);
        this.pages = pages;
    }

    public int getPages() {
        return this.pages;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotEmpty(isbn, WARNING_EMPTY);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public int getId() {
        return this.id;
    }

}