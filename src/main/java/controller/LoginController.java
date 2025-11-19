package controller;
import database.JDBConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import mapper.BookMapper;
import model.User;

import model.validator.Notification;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImplementation;
import service.user.AuthenticationService;
import view.BookDTO;
import view.BookView;
import view.LoginView;

import java.sql.Connection;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final BookService bookService;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, BookService bookService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
        this.bookService = bookService;
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasError()) {
                loginView.setActionTargetText(loginNotification.getFormatedErrors());

            }else{
                loginView.setActionTargetText("Login Successful!");

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                BookMapper bookMapper = new BookMapper();
                List<BookDTO> booksList = bookMapper.convertBookListToBookDTOList(bookService.findAll());
                BookView bookView = new BookView(stage, booksList);
            }

        }
    }
    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> regiserNotification = authenticationService.register(username, password);

            if (regiserNotification.hasError()){
                    loginView.setActionTargetText(regiserNotification.getFormatedErrors());
                }else{
                    loginView.setActionTargetText("Register successful!");
                }

        }
    }
}
