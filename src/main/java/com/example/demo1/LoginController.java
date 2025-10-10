package com.example.demo1;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField nameInput;
    @FXML
    private PasswordField passInput;

    @FXML
    public void sample(ActionEvent event) throws IOException {
        System.out.println("-------------------------------------------------------------------");
        if(nameInput.getText().equals("Admin")){
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(main.class.getResource("style.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("BentoPanda | Admin");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }
    }
}
