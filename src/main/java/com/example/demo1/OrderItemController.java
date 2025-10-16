package com.example.demo1;

import com.example.demo1.Backend.Cashier;
import com.example.demo1.Models.Info;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class OrderItemController {
    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView trashBtn;

    @FXML
    private Label quantityLabel;

    @FXML
    private Button increaseBtn;

    @FXML
    private Button decreaseBtn;

    private int qty;
    private int id;
    private CashierController cashierController;

    public void setCashierController(CashierController controller) {
        this.cashierController = controller;
    }
    private Info info;

    public void setData(Integer id, Info info) {
        this.info = info;
        this.id = id;
        qty = info.getQuantity();
        imageView.setImage(info.getImage());
        nameLabel.setText(info.getName());
        priceLabel.setText(Integer.toString(info.getPrice()));
        quantityLabel.setText(Integer.toString(qty));
    }

    @FXML
    private void increase() {
        qty++;
        quantityLabel.setText(Integer.toString(qty));
        info.setQuantity(qty);
        Cashier.updateTotal();
        cashierController.refreshTotal();
    }

    @FXML
    private void decrease() {
        if (qty > 1) {
            qty--;
            quantityLabel.setText(Integer.toString(qty));
            info.setQuantity(qty);
            Cashier.updateTotal();
            cashierController.refreshTotal();
        } else {
            Cashier.order.remove(id);
            cashierController.updateOrderList();
            Cashier.updateTotal();
            cashierController.refreshTotal();
        }
    }

    @FXML
    private void trash() {
        Cashier.order.remove(id);
        cashierController.updateOrderList();
        Cashier.updateTotal();
        cashierController.refreshTotal();
    }
}
