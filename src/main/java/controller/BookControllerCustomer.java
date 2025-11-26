package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.book.BookService;
import view.BookDTO;
import view.BookViewCustomer;

public class BookControllerCustomer {

    private final BookService bookService;
    private final BookViewCustomer bookViewCustomer;

    public BookControllerCustomer(BookViewCustomer bookViewCustomer, BookService bookService) {
        this.bookViewCustomer = bookViewCustomer;
        this.bookService = bookService;

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

            if (quantity <= 0) {
                bookViewCustomer.addDisplayAlertMessage(
                        "Buy Error",
                        "Invalid quantity",
                        "Cantitatea trebuie să fie > 0."
                );
                return;
            }

            if (quantity > bookDTO.getQuantity()) {
                bookViewCustomer.addDisplayAlertMessage(
                        "Buy Error",
                        "Insufficient stock",
                        "Nu există suficiente cărți în stoc."
                );
                return;
            }

            bookDTO.setQuantity(bookDTO.getQuantity() - quantity);

            boolean buySuccessful = bookService.buy(BookMapper.convertBookDTOToBook(bookDTO));

            if (buySuccessful) {
                bookViewCustomer.addDisplayAlertMessage(
                        "Buy Successful",
                        "Book purchased",
                        "Cumpărare reușită! Cantitate rămasă: " + bookDTO.getQuantity()
                );
                bookViewCustomer.getBookTableView().refresh();
            } else {
                bookDTO.setQuantity(bookDTO.getQuantity() + quantity);

                bookViewCustomer.addDisplayAlertMessage(
                        "Buy Error",
                        "Database error",
                        "A apărut o problemă la cumpărarea cărții."
                );
            }
        }
    }
}
