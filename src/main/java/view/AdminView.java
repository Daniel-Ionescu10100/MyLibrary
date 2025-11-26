package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;




public class AdminView {
    private Button employeeViewButton;
    private Button bookViewButton;
    private final Stage stage;
    private Scene scene;

    public AdminView(Stage primaryStage) {
        primaryStage.setTitle("Admin View");
        this.stage = primaryStage;
        GridPane gridPane = new GridPane();
        initialzeGridPane(gridPane);
        this.scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(this.scene);
        initializeSceneTitle(gridPane);
        initializeFields(gridPane);
        primaryStage.show();
    }

    private void initialzeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
    }

    private void initializeSceneTitle(GridPane gridPane) {
        Text sceneTitle = new Text("Welcome to Admin Page");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.BOLD, 20));
        gridPane.add(sceneTitle, 2, 1);
    }

    private void initializeFields(GridPane gridPane) {
        employeeViewButton = new Button("Employee View");
        gridPane.add(employeeViewButton,1,1);
        bookViewButton = new Button("Book View");
        gridPane.add(bookViewButton,1,2);
    }
    public void addEmployeeViewButtonListener(EventHandler<ActionEvent> eventHandler) {
        employeeViewButton.setOnAction(eventHandler);
    }
    public void addBookViewButtonListener(EventHandler<ActionEvent> eventHandler) {
        bookViewButton.setOnAction(eventHandler);
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }
}
