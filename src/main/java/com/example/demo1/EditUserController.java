package com.example.demo1;

import com.example.demo1.Backend.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EditUserController {
    @FXML
    private TextField idField;

    @FXML
    private TextField passField;

    @FXML
    private void saveBtn(){
        String id = idField.getText();
        String pass = passField.getText();

        boolean updated = Admin.updateUserDb(id, pass);
        if(updated){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("User updated successfully");
            alert.showAndWait();
        }
    }

    @FXML
    private void clearBtn(){
        idField.setText("");
        passField.setText("");
    }
}
