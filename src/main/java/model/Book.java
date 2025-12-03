package model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String titlu;
    private String author;
    public LocalDate publishedDate;
    public Integer quantity;
    public Double price;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
            return "Book: Id: " + id + " Title: " + titlu + " Author: " + author + " PublishedDate: " + publishedDate;
    }
}
