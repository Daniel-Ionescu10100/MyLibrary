package view;

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
import javafx.stage.Stage;

import java.util.List;

public class BookViewCustomer{

    private long currentCustomerId;

    private TableView bookTableView;
    private final ObservableList<BookDTO> booksObservableList;
    private TextField quantityTextField;
    private Label quantityLabel;
    private Button buyButton;
    private Scene scene;

    public BookViewCustomer(Stage primaryStage, List<BookDTO> books) {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        this.scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(this.scene);
        booksObservableList = FXCollections.observableArrayList(books);
        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initializeGridPage(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
    }

    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<BookDTO>();
        bookTableView.setPlaceholder(new Label("No books to display"));
        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO,String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<BookDTO, String> quantityColumn = new TableColumn<BookDTO, String>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<BookDTO, String> priceColumn = new TableColumn<BookDTO, String>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, quantityColumn, priceColumn);
        bookTableView.setItems(booksObservableList);
        gridPane.add(bookTableView,0,0,5,1);
    }
    public Scene getScene() {
        return this.scene;
    }

    private void initSaveOptions(GridPane gridPane) {

        quantityLabel = new Label("Quantity");
        gridPane.add(quantityLabel,1,1);
        quantityTextField = new TextField();
        gridPane.add(quantityTextField,2,1);


        buyButton = new Button("Buy");
        gridPane.add(buyButton,3,1);
    }


    public void addBuyButtonListener(EventHandler<ActionEvent> buyButtonListener) {
        buyButton.setOnAction(buyButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String contextInformation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contextInformation);

        alert.showAndWait();
    }


    public void addBookToObservableList(BookDTO bookDTO) {
        this.booksObservableList.add(bookDTO);
    }

    public void removeBookFromObservableList(BookDTO bookDTO) {
        this.booksObservableList.remove(bookDTO);
    }

    public TableView getBookTableView() {
        return bookTableView;
    }


    public void setCurrentCustomerId(long id) {
        this.currentCustomerId = id;
    }

    public long getCurrentCustomerId() {
        return currentCustomerId;
    }


    public String getQuantity() {
        return quantityTextField.getText();
    }

}
