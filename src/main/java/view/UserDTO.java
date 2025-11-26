package view;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDTO {
    private LongProperty id;
    private StringProperty username;
    private StringProperty role;

    public UserDTO() {}

    public UserDTO(Long id, String username, String role) {
        setId(id);
        setUsername(username);
        setRole(role);
    }

    public LongProperty idProperty() {
        if (id == null) {
            id = new SimpleLongProperty(this, "id");
        }
        return id;
    }

    public long getId() {
        return idProperty().get();
    }

    public void setId(long id) {
        idProperty().set(id);
    }

    public StringProperty usernameProperty() {
        if (username == null) {
            username = new SimpleStringProperty(this, "username");
        }
        return username;
    }

    public String getUsername() {
        return usernameProperty().get();
    }

    public void setUsername(String username) {
        usernameProperty().set(username);
    }

    public StringProperty roleProperty() {
        if (role == null) {
            role = new SimpleStringProperty(this, "role");
        }
        return role;
    }

    public String getRole() {
        return roleProperty().get();
    }

    public void setRole(String role) {
        roleProperty().set(role);
    }
}