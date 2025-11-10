package org;

import controller.LoginController;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.LoginView;

import java.sql.Connection;
import java.sql.SQLException;

import static database.Constants.Schemas.PRODUCTION;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);

        final LoginView loginView = new LoginView(primaryStage);
        new LoginController(loginView, authenticationService);
    }
}