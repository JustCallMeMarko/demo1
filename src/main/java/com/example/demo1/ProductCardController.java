package com.example.demo1;

import com.example.demo1.Backend.Cashier;
import com.example.demo1.Models.Info;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProductCardController {
    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    private CashierController cashierController;


    public void setCashierController(CashierController controller) {
        this.cashierController = controller;
    }
    private Info info;
    private String name;
    private Integer id;
    public void setData(Integer id, Info info) {
        imageView.setImage(info.getImage());
        nameLabel.setText(info.getName());
        priceLabel.setText("₱" + info.getPrice());
        Cashier.updateTotal();
        name = info.getName();
        this.info = info;
        this.id = id;
    }

    @FXML
    private void addToOrder(MouseEvent e) {
        if (Cashier.order.containsKey(id)) {
            Cashier.order.get(id).increaseQty();
            Cashier.updateTotal();
            cashierController.refreshTotal();
// increment existing quantity
        } else {
            info.setQuantity(1);                 // initialize quantity
            Cashier.order.put(id, info);
            Cashier.updateTotal();
            cashierController.refreshTotal();

        }

        if (cashierController != null) {
            cashierController.updateOrderList();
        }
    }




}
