package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImplementation;
import view.BookDTO;
import view.BookView;

import java.sql.Connection;
import java.util.List;

public class ComponentFactory {

    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static ComponentFactory instance;


    public static ComponentFactory getInstance(Stage primaryStage){
        if(instance == null){
            synchronized (ComponentFactory.class){
                if(instance == null){
                    instance = new ComponentFactory(primaryStage);
                }
            }
        }
        return instance;
    }

    private ComponentFactory(Stage primaryStage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();

        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImplementation(bookRepository);

        List<BookDTO> booksDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView = new BookView(primaryStage, booksDTOs);
        this.bookController = new BookController(bookView, bookService);
    }


    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }
}
