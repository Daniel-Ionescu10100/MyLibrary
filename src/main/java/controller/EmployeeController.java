package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import service.user.UserService;
import view.EmployeeView;
import view.UserDTO;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final UserService userService;

    public EmployeeController(EmployeeView employeeView, UserService userService) {
        this.employeeView = employeeView;
        this.userService = userService;

        this.employeeView.addAddEmployeeButtonListener(new AddEmployeeButtonListener());
        this.employeeView.addRemoveEmployeeButtonListener(new RemoveEmployeeButtonListener());

        loadUserTable();
    }

    private void loadUserTable() {
        employeeView.setUserList(userService.findAllUsers());
    }

    private class AddEmployeeButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String username = employeeView.getUserName();
            String password = employeeView.getPassword();
            String role = employeeView.getRole();

            if (username.isEmpty() || password.isEmpty()) {
                employeeView.addDisplayAlertMessage("Add Error",
                        "Invalid Fields", "Username and password cannot be empty.");
                return;
            }

            boolean ok = userService.addUser(username, password, role);

            if (ok) {
                employeeView.addUserToObservableList(new UserDTO(0L, username, role));
                employeeView.addDisplayAlertMessage("Success", "User Added",
                        "User " + username + " was created.");
            } else {
                employeeView.addDisplayAlertMessage("Error", "Add failed",
                        "Could not create user.");
            }
        }
    }

    private class RemoveEmployeeButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            UserDTO selected = employeeView.getUserTableView()
                    .getSelectionModel()
                    .getSelectedItem();

            if (selected == null) {
                employeeView.addDisplayAlertMessage("Delete Error",
                        "No user selected", "Select a user first.");
                return;
            }

            boolean ok = userService.deleteUser(selected.getId());

            if (ok) {
                employeeView.removeUserFromObservableList(selected);
                employeeView.addDisplayAlertMessage("Success",
                        "User Deleted", "User was removed.");
            } else {
                employeeView.addDisplayAlertMessage("Error",
                        "Delete failed", "Could not delete user.");
            }
        }
    }
}
