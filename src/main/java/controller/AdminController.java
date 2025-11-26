package controller;

import database.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import mapper.BookMapper;

import service.book.BookService;
import service.user.UserService;
import view.*;

import java.util.List;
import java.util.stream.Collectors;

public class AdminController {

    private final AdminView adminView;
    private final BookService bookService;
    private final UserService userService;

    public AdminController(AdminView adminView, BookService bookService, UserService userService) {
        this.adminView = adminView;
        this.bookService = bookService;
        this.userService = userService;

        this.adminView.addEmployeeViewButtonListener(new EmployeeViewButtonListener());
        this.adminView.addBookViewButtonListener(new BookViewButtonListener());
    }

    private class EmployeeViewButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Stage stage = adminView.getStage();

            List<UserDTO> initialUsers = userService.findAllUsers()
                    .stream()
                    .filter(u -> u.getRole().equals(Constants.Roles.ADMINISTRATOR)
                            || u.getRole().equals(Constants.Roles.EMPLOYEE))
                    .collect(Collectors.toList());

            EmployeeView employeeView = new EmployeeView(stage, initialUsers);

            new EmployeeController(employeeView, userService);

            stage.setScene(employeeView.getScene());
        }
    }

    private class BookViewButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Stage stage = adminView.getStage();

            BookMapper mapper = new BookMapper();
            List<BookDTO> books = mapper.convertBookListToBookDTOList(bookService.findAll());

            BookView bookView = new BookView(stage, books);
            BookController bookController = new BookController(bookView, bookService);

            stage.setScene(bookView.getScene());
        }
    }
}
