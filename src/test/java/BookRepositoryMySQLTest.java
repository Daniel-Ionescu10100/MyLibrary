import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.*;
import repository.BookRepositoryMySQL;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryMySQLTest {
    private static Connection connection;
    private static BookRepositoryMySQL bookRepository;

    @BeforeAll
    public static void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "Dan1690323");
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS book (id BIGINT AUTO_INCREMENT PRIMARY KEY, author VARCHAR(255), title VARCHAR(255), publishedDate DATE);");
        bookRepository = new BookRepositoryMySQL(connection);
        bookRepository.removeAll();
    }

    @BeforeEach
    public void clearTable() {
        bookRepository.removeAll();
    }

    @Test
    public void findAll_empty() {
        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
    }

    @Test
    public void save_and_findAll() {
        Book book = new BookBuilder().setAuthor("Author1").setTitle("Title1").setPublishedDate(LocalDate.now()).build();
        assertTrue(bookRepository.save(book));
        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
    }

    @Test
    public void save_and_findById() {
        Book book = new BookBuilder().setAuthor("Author2").setTitle("Title2").setPublishedDate(LocalDate.now()).build();
        assertTrue(bookRepository.save(book));
        List<Book> books = bookRepository.findAll();
        Long id = books.get(0).getId();
        Optional<Book> found = bookRepository.findById(id);
        assertTrue(found.isPresent());
        assertEquals("Author2", found.get().getAuthor());
    }

    @Test
    public void delete_book() {
        Book book = new BookBuilder().setAuthor("Author3").setTitle("Title3").setPublishedDate(LocalDate.now()).build();
        bookRepository.save(book);
        assertTrue(bookRepository.delete(book));
        assertEquals(0, bookRepository.findAll().size());
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }
}

