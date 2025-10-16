package com.example.demo1.Models;
import javafx.scene.image.Image;

public class Info {
    private String name;
    private String category;
    private int price;
    private Image image;
    private int quantity;

    public Info(String name, String category, int price, Image image,  int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public Image getImage() { return image; }
    public int getQuantity() { return quantity; }
    public void increaseQty() {  this.quantity++; }
    public void decreaseQty() {  this.quantity--; }
    public void setQuantity(Integer qty) {  this.quantity = qty; }
}
