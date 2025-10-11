package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private Label userMngLabel;

    private void setSelected(Label selectedLabel) {
        for (Label label : new Label[]{analyticsLabel, userMngLabel}) {
            label.getStyleClass().removeAll("selected", "menu-item");
            label.getStyleClass().add("menu-item");
        }

        selectedLabel.getStyleClass().removeAll("selected", "menu-item");
        selectedLabel.getStyleClass().add("selected");
    }

    @FXML
    private void goToAnalytics(MouseEvent e) throws IOException {
        System.out.println("go to analytics");
        Parent analyticsView = FXMLLoader.load(getClass().getResource("Analytics.fxml"));
        contentPane.getChildren().setAll(analyticsView);
        setSelected(analyticsLabel);
    }

    @FXML
    private void goToUserMng(MouseEvent e) throws IOException {
        System.out.println("go to user mng");
        Parent userMngView = FXMLLoader.load(getClass().getResource("UserMng.fxml"));
        contentPane.getChildren().setAll(userMngView);
        setSelected(userMngLabel);
    }

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
