package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField nameInput;
    @FXML
    private PasswordField passInput;
    private Parent root;
    private Stage stage;
    private Scene scene;

    public void login(ActionEvent e) throws IOException {
        String name = nameInput.getText();
        String pass = passInput.getText();
        if (name.equals("") || pass.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }  else {
            String title = "";
            if(name.equals("admin")){
                root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
                title = "Admin";
            }else if(name.equals("user")){
                root = FXMLLoader.load(getClass().getResource("Cashier.fxml"));
                title = "Cashier";
            }
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("BentoPanda | "+ title);
            stage.show();
            stage.setOnShown(ev -> {
                stage.setMaximized(true);
            });
        }
    }
    public void settings(ActionEvent e) {
        //TODO: add settings functionality
        System.out.println("settings");
    }
}
