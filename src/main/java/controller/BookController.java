package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.book.BookService;
import view.BookDTO;
import view.BookView;
import view.builder.BookDTOBuilder;

public class BookController {

    private final BookService bookService;
    private final BookView bookView;
    public BookController(BookView bookView, BookService bookService) {
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();

            if(title.isEmpty() || author.isEmpty()) {
                bookView.addDisplayAlertMessage("Save Error","Problem at Author or Title fields","Cannot have empty author or title fields");

            }else{
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).build();
                boolean saveBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));
                if(saveBook){
                    bookView.addDisplayAlertMessage("Successful","Book Added","Successfully was successfuly added to the book");
                    bookView.addBookToObservableList(bookDTO);
                }else{
                    bookView.addDisplayAlertMessage("Save Error","Problem at adding Book","There was a problem at adding the book to the addres. Please try again");

                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO != null){
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if(deletionSuccessful){
                    bookView.addDisplayAlertMessage("Delete Successful","Block deleted","Book was successfully deleted");
                    bookView.removeBookFromObservableList(bookDTO);
                }else{
                    bookView.addDisplayAlertMessage("Delete Error","Problem at deleting book","There was aa problem with the database. Please try again");

                }
            }else{
                bookView.addDisplayAlertMessage("Delete Error","Problem at deleting book","You must select a book before delete button");

            }
        }
    }

}
