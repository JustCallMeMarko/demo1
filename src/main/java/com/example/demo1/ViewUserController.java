package com.example.demo1;

import com.example.demo1.Backend.Admin;
import com.example.demo1.Models.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewUserController {

    @FXML
    private TableView<Users> userTable;

    @FXML
    private TableColumn<Users, Integer> idColumn;

    @FXML
    private TableColumn<Users, String> nameColumn;

    @FXML
    private TableColumn<Users, String> roleColumn;

    @FXML
    public void initialize() {
        // Link the columns to your model fields
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Example data
        ObservableList<Users> userList = Admin.getUsers();
        userTable.setItems(userList);
    }
}
