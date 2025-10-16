package com.example.demo1;

import com.example.demo1.Backend.Cashier;
import com.example.demo1.Backend.Login;
import com.example.demo1.Models.Info;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CashierController {
    private String selected = "Bento";
    private HashMap<Integer, Info> products;

    @FXML
    private GridPane menuGridPane;

    @FXML
    private Label bentoLabel;

    @FXML
    private Label ramenLabel;

    @FXML
    private Label sidesLabel;

    @FXML
    private Label drinksLabel;

    @FXML
    ScrollPane scrollList;

    @FXML
    private VBox orderListVBox;

    @FXML
    private Label totalLabel;

    @FXML
    private Label accName;

    @FXML
    private Label discountLabel;

    @FXML
    private TextField cashField;

    @FXML
    private Label changeLabel;

    @FXML
    private Button payButton;

    private boolean paid = false;

    @FXML
    private TextField dicountCode;
    private boolean limited = false;
    public void updateOrderList() {
        orderListVBox.getChildren().clear();

        for (Map.Entry<Integer, Info> entry : Cashier.order.entrySet()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderItem.fxml"));
                AnchorPane orderItem = loader.load();

                OrderItemController cardController = loader.getController();
                cardController.setData(entry.getKey(), entry.getValue());
                cardController.setCashierController(this);
                orderListVBox.getChildren().add(orderItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void initialize() {
        accName.setText(Login.name);
        Cashier.getItems();
        products = Cashier.myProducts;
        updateMenuGrid();
        totalLabel.setText(Integer.toString(Cashier.total));
    }

    public void refreshTotal(){
        totalLabel.setText("₱" + Cashier.total);
    }
    private void updateMenuGrid() {
        menuGridPane.getChildren().clear();

        int columns = 2;
        int row = 0;
        int col = 0;

        for (Map.Entry<Integer, Info> info : products.entrySet()) {
            if (info.getValue().getImage() != null && info.getValue().getCategory().equals(selected)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductCard.fxml"));
                    HBox card = loader.load();

                    ProductCardController cardController = loader.getController();
                    cardController.setData(info.getKey(),  info.getValue());
                    cardController.setCashierController(this);

                    menuGridPane.add(card, col, row);

                    col++;
                    if (col == columns) {
                        col = 0;
                        row++;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void setSelected(Label selectedLabel) {
        for (Label label : new Label[]{bentoLabel, ramenLabel, sidesLabel, drinksLabel}) {
            label.getStyleClass().removeAll("selected", "menu-item");
            label.getStyleClass().add("menu-item");
        }
        selectedLabel.getStyleClass().removeAll("selected", "menu-item");
        selectedLabel.getStyleClass().add("selected");
        switch(selectedLabel.getText()) {
            case "Bento":
                selected = "Bento";
                break;
            case "Ramen":
                selected = "Ramen";
                break;
            case "Sides":
                selected = "Sides";
                break;
            case "Drinks":
                selected = "Drinks";
                break;
        }
        updateMenuGrid();
    }

    @FXML
    private void setBento(MouseEvent e) {
        setSelected(bentoLabel);
    }

    @FXML
    private void setdrinks(MouseEvent e) {
        setSelected(drinksLabel);
    }

    @FXML
    private void setRamen(MouseEvent e) {
        setSelected(ramenLabel);
    }

    @FXML
    private void setSides(MouseEvent e) {
        setSelected(sidesLabel);
    }

    @FXML
    private void clearOrder(MouseEvent e) {
        Cashier.order.clear();
        updateDiscount("₱0");
        resetCodeLimit();
        updateOrderList();
        Cashier.updateTotal();
        refreshTotal();
        cashField.clear();
        changeLabel.setText("₱0");
        payButton.setDisable(false);
        paid = false;
    }

    @FXML
    private void applyDiscount(MouseEvent e) {
        if(limited) {
            return;
        }
        int discount = 0;
        if(dicountCode.getText().equals("SCPWD")) {
            discount  = (int) (Cashier.total * 0.20);
            limited = true;
        }else if(dicountCode.getText().equals("BENTO2025")) {
            discount  = (int) (Cashier.total * 0.10);
            limited = true;
        }
        Cashier.total -= discount;
        updateDiscount(Integer.toString(discount));
        refreshTotal();
    }

    public void resetCodeLimit() {
        limited = false;
    }
    public void updateDiscount(String discount) {
        dicountCode.setText("");
        discountLabel.setText(discount);
    }

    @FXML
    private void pay(MouseEvent e) throws IOException {
        if (paid) {
            clearOrder(e);
            return;
        }

        if (cashField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter cash amount.");
            alert.showAndWait();
            return;
        }

        int cash;
        try {
            cash = Integer.parseInt(cashField.getText());
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Cash amount must be a number.");
            alert.showAndWait();
            return;
        }

        int change = cash - Cashier.total;
        if (change < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Payment Unsuccessful");
            alert.setHeaderText(null);
            alert.setContentText("Insufficient funds.");
            alert.showAndWait();
            return;
        }

        changeLabel.setText("₱" + change);

        boolean res = Cashier.payDb();
        if (res) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully paid order and added to the database.");
            alert.showAndWait();

            paid = true;
            payButton.setDisable(true); // ✅ disable after successful payment
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Payment Failed");
            alert.setHeaderText(null);
            alert.setContentText("Payment record cannot be added to the database due to an unknown error.");
            alert.showAndWait();
        }
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
