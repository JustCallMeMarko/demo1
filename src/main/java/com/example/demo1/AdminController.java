package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        System.out.println("Go to User Management");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMng.fxml"));
        Parent userMngView = loader.load();

        Button addUserBtn = (Button) userMngView.lookup("#addUserBtn");
        Button viewUserBtn = (Button) userMngView.lookup("#viewUserBtn");

        if (addUserBtn != null) {
            addUserBtn.setOnMouseClicked(evt -> {
                try {
                    goToAddUser(evt);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (viewUserBtn != null) {
            viewUserBtn.setOnMouseClicked(evt -> {
                try {
                    goToViewUser(evt);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

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

    @FXML
    private void goToAddUser(MouseEvent e) throws IOException {
        System.out.println("Go to Add User");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
        Parent addUserView = loader.load();

        Button backBtn = (Button) addUserView.lookup("#backBtn");
        if (backBtn != null) {
            backBtn.setOnMouseClicked(evt -> {
                try {
                    goToUserMng(evt);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        contentPane.getChildren().setAll(addUserView);
    }


    @FXML
    private void goToViewUser(MouseEvent e) throws IOException {
        System.out.println("Go to Add User");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewUser.fxml"));
        Parent addUserView = loader.load();

        Button backBtn = (Button) addUserView.lookup("#backBtn");
        if (backBtn != null) {
            backBtn.setOnMouseClicked(evt -> {
                try {
                    goToUserMng(evt);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        contentPane.getChildren().setAll(addUserView);
    }
}
