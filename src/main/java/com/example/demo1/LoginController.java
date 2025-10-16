package com.example.demo1;

import com.example.demo1.Backend.Admin;
import com.example.demo1.Backend.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField nameInput;

    @FXML
    private PasswordField passInput;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private FlowPane myPane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // --- Center the FlowPane dynamically ---
    @FXML
    public void initialize() {
        // Run once scene is ready
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Center on initial layout and whenever size changes
                rootPane.widthProperty().addListener((o, oldW, newW) -> centerPane());
                rootPane.heightProperty().addListener((o, oldH, newH) -> centerPane());
                centerPane();
            }
        });
    }

    private void centerPane() {
        double x = (rootPane.getWidth() - myPane.getWidth()) / 2;
        double y = (rootPane.getHeight() - myPane.getHeight()) / 2;

        // Only reposition if the pane has a valid size
        if (x >= 0 && y >= 0) {
            myPane.setLayoutX(x);
            myPane.setLayoutY(y);
        }
    }

    // --- Handles Login Logic ---
    @FXML
    public void login(ActionEvent e) throws IOException {
        String name = nameInput.getText().trim();
        String pass = passInput.getText().trim();

        if (name.isEmpty() || pass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in both username and password.");
            alert.showAndWait();
            return;
        }

        String file = "";
        String title = "";

        String role = new Login().loginDb(name, pass);

        System.out.println("Login Success  " +  role + " " + name + " " + pass);
        if (role == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid credentials.");
            alert.showAndWait();
            return;
        }
        if (role.equalsIgnoreCase("admin")) {
            file = "Admin.fxml";
            title = "Admin Analytics";
        } else if (role.equalsIgnoreCase("user")) {
            file = "Cashier.fxml";
            title = "Cashier";
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        root = loader.load();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("BentoPanda | " + title);
        stage.setMaximized(true);
        stage.show();
    }
    @FXML
    public void forgotPassword(MouseEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgot Password");
        alert.setHeaderText(null);
        alert.setContentText("To reset your password, please contact your administrator.");
        alert.showAndWait();
        return;
    }
}
