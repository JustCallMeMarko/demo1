package com.example.demo1;

import com.example.demo1.Backend.Admin;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AdddItemController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private ChoiceBox<String> menuChoices;

    @FXML
    private ImageView imagePreview;

    private File selectedImage;

    @FXML
    private void initialize() {
        menuChoices.getItems().addAll("Bento", "Ramen", "Sides", "Drinks");
        menuChoices.setValue("Bento");
    }

    @FXML
    private void onAddItem() {
        try {
            String name = nameField.getText();
            int price = priceField.getText().isEmpty() ? 0 : Integer.parseInt(priceField.getText());
            String category = menuChoices.getValue();

            if(name.isEmpty() || price <= 0 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Warning");
                alert.setContentText("Please put data in all of the fields.");
                alert.showAndWait();
                return;
            }
            if (selectedImage == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Warning");
                alert.setContentText("Please select an image before adding.");
                alert.showAndWait();
                return;
            }

            boolean success = Admin.addItemDb(name, price, category, selectedImage);

            if (success) {
                priceField.clear();
                nameField.clear();
                menuChoices.setValue("Bento");
                selectedImage = null;
                imagePreview.setImage(null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("Item added successfully.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Error adding an item.");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Item Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            selectedImage = file;
            imagePreview.setImage(new Image(file.toURI().toString()));
        }
    }
}
