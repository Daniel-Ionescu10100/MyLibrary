package model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String titlu;
    private String author;
    public LocalDate publishedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return titlu;
    }

    public void setTitle(String titlu) {
        this.titlu = titlu;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
            return "Book: Id: " + id + " Title: " + titlu + " Author: " + author + " PublishedDate: " + publishedDate;
    }
}
