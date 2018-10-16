package Wolox.training.models;

import Wolox.training.DAO.BookDAO;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "genre")
    private String genre;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle", nullable = false)
    private String subtitle;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "year", nullable = false)
    private String year;

    @Column(name = "pages", nullable = false)
    private int pages;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    public Book() {

    }

    public Book(BookDAO bookDAO) {
        this.title = bookDAO.getTitle();
        this.subtitle = bookDAO.getSubtitle();
        this.publisher = bookDAO.getPublishers();
        this.isbn = bookDAO.getIsbn();
        this.year = bookDAO.getPublishDate();
        this.author = bookDAO.getAuthors().stream().findFirst().get();
        this.image = bookDAO.getCover();
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return this.year;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPages() {
        return this.pages;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public int getId() { return this.id; }

}