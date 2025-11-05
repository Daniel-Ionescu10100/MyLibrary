package org;

import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMock;
import repository.BookRepositoryMySQL;
import service.BookService;
import service.BookServiceImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello World");
//
        Book book = new BookBuilder()
                .setTitle("Colt Alb")
                .setAuthor("Daniel")
                .setPublishedDate(LocalDate.of(1960, 12, 27))
                .build();
        System.out.println(book);
//
//        BookRepository bookRepository = new BookRepositoryMock();
//        bookRepository.save(book);
//        bookRepository.save(new BookBuilder()
//                .setTitle("Morometii").setAuthor("Paul")
//                .setPublishedDate(LocalDate.of(1990, 2, 2))
//                .build());
//        System.out.println(bookRepository.findAll());
//        bookRepository.removeAll();
//        System.out.println(bookRepository.findAll());

        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository = new BookRepositoryMySQL(connection);
        BookService bookService = new BookServiceImplementation(bookRepository);

        bookService.save(book);
        System.out.println(bookRepository.findAll());

        Book coltAlb = new BookBuilder()
                .setTitle("Colt Alb")
                .setAuthor("Daniel")
                .setPublishedDate(LocalDate.of(1960, 12, 27))
                .build();
        bookService.save(coltAlb);
        System.out.println(bookRepository.findAll());
        bookService.delete(coltAlb);
        bookService.save(book);
        System.out.println(bookService.findAll());
    }
}