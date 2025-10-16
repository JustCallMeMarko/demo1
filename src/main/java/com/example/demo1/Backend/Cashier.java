package com.example.demo1.Backend;

import com.example.demo1.Models.Info;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.sql.*;
import java.util.Map;

public class Cashier {
    private static String url = "jdbc:mysql://localhost:3306/bentopanda";
    private static String user = "root";
    private static String pass = "mark";
    public static  HashMap<Integer, Info> myProducts = new HashMap<>();
    public static HashMap<Integer, Info> order = new LinkedHashMap<>();
    public static int total = 0;

    public static void updateTotal() {
        int sum = 0;
        for (Info info : order.values()) {
            sum += info.getPrice() * info.getQuantity();
        }
        total = sum;
    }

    public static void getItems(){

        String sql = "SELECT * FROM products";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             ResultSet rs = stmt.executeQuery();

             while (rs.next()) {
                 int  id = rs.getInt("product_id");
                 String name = rs.getString("name");
                 String category = rs.getString("category");
                 int price  = rs.getInt("price");
                 InputStream is = rs.getBinaryStream("image");
                 Image img = new Image(is);
                 myProducts.put(id, new Info(name, category, price, img, 0));
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean payDb() {
        String sql = "INSERT INTO transactions (user_id, total, transact_date) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Login.user_id);
            pstmt.setInt(2, total);
            pstmt.setDate(3, new Date(System.currentTimeMillis()));

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addItemDb(String name, int price, File image) {
        String sql = "INSERT INTO users (name, price, image) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            FileInputStream fis = new FileInputStream(image);
            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.setBinaryStream(3, fis, (int) image.length());
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
