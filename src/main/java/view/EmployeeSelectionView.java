package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.UserDTO;

public class EmployeeSelectionView {

    private final Stage stage;
    private final TableView<UserDTO> employeeTable;
    private final Button selectButton;
    private UserDTO selectedEmployee;

    public EmployeeSelectionView() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select Employee");

        employeeTable = new TableView<>();
        TableColumn<UserDTO, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        employeeTable.getColumns().addAll(idColumn, usernameColumn);

        selectButton = new Button("Select Employee");

        VBox layout = new VBox(10, employeeTable, selectButton);
        stage.setScene(new Scene(layout, 300, 400));
    }

    public void setEmployeeList(ObservableList<UserDTO> employees) {
        employeeTable.setItems(employees);
    }

    public void addSelectButtonListener(Runnable listener) {
        selectButton.setOnAction(e -> listener.run());
    }

    public void show() {
        stage.showAndWait();
    }

    public UserDTO getSelectedEmployee() {
        selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        return selectedEmployee;
    }
    public void close() {
        stage.close();
    }

}
