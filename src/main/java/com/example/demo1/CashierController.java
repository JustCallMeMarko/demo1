package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CashierController {

    @FXML
    private void logout(MouseEvent e) throws IOException {
        System.out.println("Logging out...");

        Parent loginView = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        Scene scene = new Scene(loginView);
        stage.setScene(scene);
        stage.setTitle("BentoPanda | Login");
        stage.setMaximized(true);
        stage.show();

        stage.setOnShown(ev -> stage.setMaximized(true));
    }
}
