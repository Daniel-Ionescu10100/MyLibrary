package controller;

import database.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import mapper.BookMapper;
import model.User;
import model.validator.Notification;
import service.book.BookService;
import service.sale.SaleService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.*;

import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final BookService bookService;
    private final UserService userService;
    private final SaleService saleService;

    public LoginController(LoginView loginView,
                           AuthenticationService authenticationService,
                           BookService bookService,
                           UserService userService,
                           SaleService saleService) {

        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.bookService = bookService;
        this.userService = userService;
        this.saleService = saleService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasError()) {
                loginView.setActionTargetText(loginNotification.getFormatedErrors());
                return;
            }

            loginView.setActionTargetText("Login Successful!");
            User user = loginNotification.getResult();


            boolean isAdmin = user.getRoles().stream()
                    .anyMatch(role -> role.getRole().equals(Constants.Roles.ADMINISTRATOR));

            boolean isEmployee = user.getRoles().stream()
                    .anyMatch(role -> role.getRole().equals(Constants.Roles.EMPLOYEE));

            boolean isCustomer = user.getRoles().stream()
                    .anyMatch(role -> role.getRole().equals(Constants.Roles.CUSTOMER));

            Stage stage = loginView.getStage();
            BookMapper bookMapper = new BookMapper();

            if (isAdmin) {

                AdminView adminView = new AdminView(stage);

                AdminController adminController =
                        new AdminController(adminView, bookService, userService, authenticationService, saleService);

                stage.setScene(adminView.getScene());
                return;
            }

            if (isEmployee) {

                List<BookDTO> booksList = bookMapper.convertBookListToBookDTOList(bookService.findAll());
                BookView bookView = new BookView(stage, booksList);
                BookController bookController = new BookController(bookView, bookService);
                stage.setScene(bookView.getScene());
            }

            if (isCustomer) {

                List<BookDTO> booksList =
                        bookMapper.convertBookListToBookDTOList(bookService.findAll());

                BookViewCustomer bookViewCustomer = new BookViewCustomer(stage, booksList);

                bookViewCustomer.setCurrentCustomerId(user.getId());
                BookControllerCustomer customerController =
                        new BookControllerCustomer(bookViewCustomer, bookService, userService, saleService);

                stage.setScene(bookViewCustomer.getScene());
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification =
                    authenticationService.register(username, password);

            if (registerNotification.hasError()) {
                loginView.setActionTargetText(registerNotification.getFormatedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }
}
