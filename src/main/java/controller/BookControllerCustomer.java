package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import service.book.BookService;
import service.sale.SaleService;
import service.user.UserService;
import view.BookDTO;
import view.BookViewCustomer;
import view.EmployeeSelectionView;
import view.UserDTO;
import mapper.BookMapper;

import java.util.List;

public class BookControllerCustomer {

    private final BookService bookService;
    private final UserService userService;
    private final SaleService saleService;
    private final BookViewCustomer bookViewCustomer;

    public BookControllerCustomer(BookViewCustomer bookViewCustomer, BookService bookService,
                                  UserService userService, SaleService saleService) {
        this.bookViewCustomer = bookViewCustomer;
        this.bookService = bookService;
        this.userService = userService;
        this.saleService = saleService;

        this.bookViewCustomer.addBuyButtonListener(new BuyButtonListener());
    }

    private class BuyButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

            BookDTO bookDTO = (BookDTO) bookViewCustomer.getBookTableView()
                    .getSelectionModel()
                    .getSelectedItem();

            if (bookDTO == null) {
                bookViewCustomer.addDisplayAlertMessage(
                        "Buy Error",
                        "No book selected",
                        "Trebuie să selectați o carte înainte de a cumpăra."
                );
                return;
            }

            String qtyStr = bookViewCustomer.getQuantity();
            if (qtyStr == null || qtyStr.isEmpty()) {
                bookViewCustomer.addDisplayAlertMessage(
                        "Buy Error",
                        "Invalid quantity",
                        "Trebuie să introduceți o cantitate."
                );
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(qtyStr);
            } catch (NumberFormatException e) {
                bookViewCustomer.addDisplayAlertMessage(
                        "Buy Error",
                        "Invalid quantity",
                        "Cantitatea trebuie să fie un număr."
                );
                return;
            }

            if (quantity <= 0 || quantity > bookDTO.getQuantity()) {
                bookViewCustomer.addDisplayAlertMessage(
                        "Buy Error",
                        "Invalid quantity",
                        "Cantitate invalidă sau insuficientă în stoc."
                );
                return;
            }
            userService.findAllUsers().forEach(u ->
                    System.out.println("USER = " + u.getUsername() + " ROLE = " + u.getRole())
            );

            List<UserDTO> employees = userService.findAllUsers()
                    .stream()
                    .filter(u -> u.getRole().toLowerCase().contains("employee"))
                    .toList();

            EmployeeSelectionView employeeSelectionView = new EmployeeSelectionView();
            employeeSelectionView.setEmployeeList(FXCollections.observableArrayList(employees));

            employeeSelectionView.addSelectButtonListener(() -> {
                UserDTO selectedEmployee = employeeSelectionView.getSelectedEmployee();
                if (selectedEmployee == null) {
                    bookViewCustomer.addDisplayAlertMessage(
                            "Selection Error",
                            "No employee selected",
                            "Trebuie să selectați un angajat."
                    );
                    return;
                }

                double totalCost = bookDTO.getPrice() * quantity;

                boolean saleRecorded = saleService.recordSale(
                        selectedEmployee.getId(),
                        bookViewCustomer.getCurrentCustomerId(),
                        bookDTO.getAuthor(),
                        bookDTO.getTitle(),
                        quantity,
                        totalCost
                );


                if (saleRecorded) {
                    bookDTO.setQuantity(bookDTO.getQuantity() - quantity);
                    bookService.buy(BookMapper.convertBookDTOToBook(bookDTO));
                    bookViewCustomer.getBookTableView().refresh();
                    bookViewCustomer.addDisplayAlertMessage(
                            "Purchase Successful",
                            "Success",
                            "Cumpărare realizată cu succes!"
                    );
                } else {
                    bookViewCustomer.addDisplayAlertMessage(
                            "Purchase Error",
                            "Error",
                            "Nu s-a putut înregistra tranzacția."
                    );
                }

                employeeSelectionView.close();
            });

            employeeSelectionView.show();
        }
    }
}
