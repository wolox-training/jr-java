package Wolox.training.DAO;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookDAO {

    private String isbn;
    private String title;
    private String subtitle;
    private String publishers;
    private String publishDate;
    private int pages;
    private ArrayList<String> authors = new ArrayList<String>();

    public BookDAO() {
    }

    public BookDAO(String isbn, String title, String subtitle, String publishers, String publishDate, int pages) {
        this.isbn = isbn;
        this.title = title;
        this.subtitle = subtitle;
        this.publishers = publishers;
        this.publishDate = publishDate;
        this.pages = pages;
    }

    public void addAuthor(String author) {
        this.authors.add(author);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

}
