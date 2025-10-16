package com.example.demo1;

import com.example.demo1.Backend.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddUserController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField passField;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private void initialize() {
        roleChoiceBox.getItems().addAll("admin", "user");
        roleChoiceBox.setValue("user");
    }

    @FXML
    private void addUser() {
        String name = nameField.getText().trim();
        String pass = passField.getText().trim();
        String role = roleChoiceBox.getSelectionModel().getSelectedItem().toString();

        Admin.addUserDb(name, pass, role);
        nameField.clear();
        passField.clear();
    }

    @FXML
    private void clearFields() {
        nameField.clear();
        passField.clear();
    }
}
