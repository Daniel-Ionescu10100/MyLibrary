package view.builder;

import model.builder.BookBuilder;
import view.BookDTO;

public class BookDTOBuilder {
    private BookDTO bookDTO;

    public BookDTOBuilder() {
        bookDTO = new BookDTO();
    }

    public BookDTOBuilder setAuthor(String author){
        bookDTO.setAuthor(author);
        return this;
    }

    public BookDTOBuilder setTitle(String title){
        bookDTO.setTitle(title);
        return this;
    }

    public BookDTOBuilder setQuantity(int quantity){
        bookDTO.setQuantity(quantity);
        return this;
    }

    public BookDTOBuilder setPrice(double price){
        bookDTO.setPrice(price);
        return this;
    }

    public BookDTO build() {
        return bookDTO;
    }

}
