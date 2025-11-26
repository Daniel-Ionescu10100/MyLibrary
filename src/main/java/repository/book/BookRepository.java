package repository.book;

import model.Book;

import java.util.*;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    boolean save(Book book);
    boolean delete(Book book);
    boolean buy(Book book);
    void removeAll();
}
