package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void login(ActionEvent event){
        System.out.println("Login");
    }
}
