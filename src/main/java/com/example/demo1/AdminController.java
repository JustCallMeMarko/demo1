package com.example.demo1;

import com.example.demo1.Backend.Admin;
import com.example.demo1.Models.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Label analyticsLabel;

    @FXML
    private Label addUserLabel;

    @FXML
    private Label viewUserLabel;

    @FXML
    private Label editPassLabel;

    @FXML
    private Label addItemLabel;

    //Change Design of Selected Menu
    private void setSelected(Label selectedLabel) {
        for (Label label : new Label[]{analyticsLabel, addUserLabel, viewUserLabel, editPassLabel, addItemLabel}) {
            label.getStyleClass().removeAll("selected", "menu-item");
            label.getStyleClass().add("menu-item");
        }
        selectedLabel.getStyleClass().removeAll("selected", "menu-item");
        selectedLabel.getStyleClass().add("selected");
    }

    //Go to Analytics View
    @FXML
    private void goToAnalytics(MouseEvent e) throws IOException {
        System.out.println("go to analytics");
        Parent analyticsView = FXMLLoader.load(getClass().getResource("Analytics.fxml"));
        contentPane.getChildren().setAll(analyticsView);
        setSelected(analyticsLabel);
    }

    @FXML
    private void goToAddUser(MouseEvent e) throws IOException {
        System.out.println("go to add user");
        Parent analyticsView = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
        contentPane.getChildren().setAll(analyticsView);
        setSelected(addUserLabel);
    }
    @FXML
    private void goToViewUser(MouseEvent e) throws IOException {
        System.out.println("go to view user");
        Parent analyticsView = FXMLLoader.load(getClass().getResource("ViewUser.fxml"));
        contentPane.getChildren().setAll(analyticsView);
        setSelected(viewUserLabel);
    }

    @FXML
    private void goToEditPass(MouseEvent e) throws IOException {
        System.out.println("go to view user");
        Parent analyticsView = FXMLLoader.load(getClass().getResource("EditPass.fxml"));
        contentPane.getChildren().setAll(analyticsView);
        setSelected(editPassLabel);
    }

    @FXML
    private void goToAddItem(MouseEvent e) throws IOException {
        System.out.println("go to view user");
        Parent analyticsView = FXMLLoader.load(getClass().getResource("AddItem.fxml"));
        contentPane.getChildren().setAll(analyticsView);
        setSelected(addItemLabel);
    }

    //Go Back to Login View
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
