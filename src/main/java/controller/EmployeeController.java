package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.User;
import model.validator.Notification;
import service.sale.SaleService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.EmployeeView;
import view.UserDTO;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final SaleService saleService;
    public EmployeeController(EmployeeView employeeView, UserService userService, AuthenticationService authenticationService, SaleService saleService) {
        this.employeeView = employeeView;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.saleService = saleService;

        this.employeeView.addAddEmployeeButtonListener(new AddEmployeeButtonListener());
        this.employeeView.addRemoveEmployeeButtonListener(new RemoveEmployeeButtonListener());
        this.employeeView.addMakeReportButtonListener(new MakeReportButtonListener());
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

           //acum cu hash
            Notification<Boolean> registerNotification =
                    authenticationService.register(username, password);

            if (registerNotification.hasError()) {
                employeeView.addDisplayAlertMessage("Error", "Add failed",
                        registerNotification.getFormatedErrors());
                return;
            }

            User createdUser = userService.findByUsername(username);

            userService.assignRoleToUser(createdUser.getId(), role.toLowerCase());

            employeeView.addUserToObservableList(new UserDTO(
                    createdUser.getId(), username, role
            ));
            employeeView.addDisplayAlertMessage("Success", "User Added",
                    "User " + username + " was created.");
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

    private class MakeReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            UserDTO selectedEmployee = employeeView.getUserTableView()
                    .getSelectionModel()
                    .getSelectedItem();

            if (selectedEmployee == null) {
                employeeView.addDisplayAlertMessage("Report Error", "No employee selected", "Trebuie să selectați un angajat.");
                return;
            }

            try {
                int totalSales = saleService.getSalesCountLastMonth(selectedEmployee.getId());
                double totalRevenue = saleService.getSalesRevenueLastMonth(selectedEmployee.getId());

                String pdfPath = "Report_" + selectedEmployee.getUsername() + ".pdf";
                com.itextpdf.text.Document document = new com.itextpdf.text.Document();
                com.itextpdf.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(pdfPath));
                document.open();
                document.add(new com.itextpdf.text.Paragraph("Raport pentru angajat: " + selectedEmployee.getUsername()));
                document.add(new com.itextpdf.text.Paragraph("Număr vânzări în ultima lună: " + totalSales));
                document.add(new com.itextpdf.text.Paragraph("Total venituri în ultima lună: " + totalRevenue));
                document.close();

                employeeView.addDisplayAlertMessage("Report Created", "PDF generat", "Raportul a fost creat la: " + pdfPath);

            } catch (Exception e) {
                e.printStackTrace();
                employeeView.addDisplayAlertMessage("Report Error", "Eroare PDF", "Nu s-a putut genera raportul.");
            }
        }
    }
}
