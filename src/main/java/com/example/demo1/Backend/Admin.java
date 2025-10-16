package com.example.demo1.Backend;

import com.example.demo1.Models.Info;
import com.example.demo1.Models.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Admin {
    private static String url = "jdbc:mysql://localhost:3306/bentopanda";
    private static String user = "root";
    private static String pass = "mark";

    public static void addUserDb(String name, String password, String role) {
        String sql = "INSERT INTO users (name, pass, role) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("✅ User added successfully!");
            } else {
                System.out.println("⚠️ No user was added.");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateUserDb(String id, String password) {
        String sql = "UPDATE users SET pass = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            pstmt.setString(1, password);
            pstmt.setInt(2,Integer.parseInt(id));
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ObservableList<Users> getUsers(){
        String sql = "SELECT user_id, name, role FROM users";
        ObservableList<Users> userList = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                userList.add(new Users(id, name, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public static Map<String, Integer> getUserPerformance(){
        Map<String, Integer> userSales = new HashMap<>();
        String sql = """
           SELECT\s
               u.user_id,
               u.name AS user_name,
               SUM(t.total) AS total_sales
           FROM transactions t
           INNER JOIN users u\s
               ON u.user_id = t.user_id
           GROUP BY u.user_id, u.name
           ORDER BY total_sales DESC;
        """;
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("user_name");
                int total = rs.getInt("total_sales");
                userSales.put(name, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userSales;
    }
    public static Map<String, Integer> getWeeklySales() {
        Map<String, Integer> weeklySales = new HashMap<>();
        String sql = "SELECT DAYNAME(transact_date) as day, SUM(total) as daily_total " +
                "FROM transactions " +
                "WHERE transact_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                "GROUP BY DAYNAME(transact_date), DAYOFWEEK(transact_date) " +
                "ORDER BY DAYOFWEEK(transact_date)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String day = rs.getString("day");
                int total = rs.getInt("daily_total");
                weeklySales.put(day, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeklySales;
    }

    public static Map<String, Integer> getMonthlyRevenue(int year) {
        Map<String, Integer> monthlyRevenue = new HashMap<>();
        String sql = """
        SELECT
            MONTH(transact_date) AS month_number,
            MONTHNAME(transact_date) AS month_name,
            SUM(total) AS monthly_total
        FROM transactions
        WHERE YEAR(transact_date) = ?
        GROUP BY MONTH(transact_date), MONTHNAME(transact_date)
        ORDER BY MONTH(transact_date);
    """;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // ✅ must match alias in SQL
                String month = rs.getString("month_name");
                int total = rs.getInt("monthly_total");
                monthlyRevenue.put(month, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthlyRevenue;
    }

    public static Map<String, Integer> getSalesByMonth(int year, int month) {
        Map<String, Integer> monthlyData = new HashMap<>();
        String sql = "SELECT SUM(total) as total_sales, COUNT(transact_id) as total_transactions " +
                "FROM transactions " +
                "WHERE YEAR(transact_date) = ? AND MONTH(transact_date) = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                monthlyData.put("total_sales", rs.getInt("total_sales"));
                monthlyData.put("total_transactions", rs.getInt("total_transactions"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyData;
    }

    public static boolean addItemDb(String name, int price, String category,  File image) {
        String sql = "INSERT INTO products (name, category, price, image) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            FileInputStream fis = new FileInputStream(image);
            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setInt(3, price);
            pstmt.setBinaryStream(4, fis, (int) image.length());
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }else{
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
