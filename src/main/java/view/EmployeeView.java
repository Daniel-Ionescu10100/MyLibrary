package view;

import controller.EmployeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class EmployeeView {
    private TableView<UserDTO> userTableView;
    private ObservableList<UserDTO> usersObservableList;
    private Button employeeViewButton;
    private Button bookViewButton;
    private final Stage stage;
    private Scene scene;
    private Button addEmployeeButton;
    private Button removeEmployeeButton;
    private Label userNameLabel;
    private TextField userNameTextField;
    private Label passwordLabel;
    private PasswordField passwordTextField;
    private Label roleLabel;
    private ComboBox<String> roleComboBox;
    private Button makeReportButton;


    public EmployeeView(Stage primaryStage, List<UserDTO> users) {
        primaryStage.setTitle("Admin View");
        this.stage = primaryStage;
        GridPane gridPane = new GridPane();
        initialzeGridPane(gridPane);
        this.scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(this.scene);
        initializeSceneTitle(gridPane);

        usersObservableList = FXCollections.observableArrayList(users);
        initTableView(gridPane);

        initializeFields(gridPane);
        primaryStage.show();
    }

    public void initialzeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));

    }

    public void initializeSceneTitle(GridPane gridPane) {
        Text sceneTitle = new Text("Welcome to EmployeView Page");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.BOLD, 20));
        gridPane.add(sceneTitle, 0, 0, 7, 1);
    }

    public void initTableView(GridPane gridPane) {
        userTableView = new TableView<UserDTO>();
        userTableView.setPlaceholder(new Label("No employees to display"));

        TableColumn<UserDTO, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserDTO, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTableView.getColumns().addAll(idColumn, usernameColumn, roleColumn);
        userTableView.setItems(usersObservableList);

        gridPane.add(userTableView, 0, 1, 5, 1);
    }

    public void initializeFields(GridPane gridPane) {
        userNameLabel = new Label("Username");
        gridPane.add(userNameLabel, 1,2);
        userNameTextField = new TextField();
        gridPane.add(userNameTextField, 2, 2);

        passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 3,2);
        passwordTextField = new PasswordField();
        gridPane.add(passwordTextField, 4, 2);

        roleLabel = new Label("Role");
        gridPane.add(roleLabel, 1, 3);
        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(database.Constants.Roles.ADMINISTRATOR, database.Constants.Roles.EMPLOYEE);
        roleComboBox.setValue(database.Constants.Roles.EMPLOYEE);
        gridPane.add(roleComboBox, 2, 3);

        addEmployeeButton = new Button("Add Employee");
        gridPane.add(addEmployeeButton, 5, 2);
        removeEmployeeButton = new Button("Remove Employee");
        gridPane.add(removeEmployeeButton, 6, 2);
        makeReportButton = new Button("Make Report");
        gridPane.add(makeReportButton, 5, 3);
    }


    public void addUserToObservableList(UserDTO userDTO) {
        this.usersObservableList.add(userDTO);
    }

    public void removeUserFromObservableList(UserDTO userDTO) {
        this.usersObservableList.remove(userDTO);
    }

    public TableView<UserDTO> getUserTableView() {
        return userTableView;
    }


    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void addAddEmployeeButtonListener(EventHandler<ActionEvent> event) {
        this.addEmployeeButton.setOnAction(event);
    }
    public void addRemoveEmployeeButtonListener(EventHandler<ActionEvent> event) {
        this.removeEmployeeButton.setOnAction(event);
    }

    public String getUserName() {
        return userNameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public String getRole() {
        return roleComboBox.getValue();
    }

    public void setUserList(List<UserDTO> users) {
        usersObservableList.setAll(users);
    }

    public void addDisplayAlertMessage(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addMakeReportButtonListener(EventHandler<ActionEvent> event) {
        this.makeReportButton.setOnAction(event);
    }
}