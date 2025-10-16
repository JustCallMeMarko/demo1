package com.example.demo1.Backend;

import java.sql.*;

public class Login {
    private static String url = "jdbc:mysql://localhost:3306/bentopanda";
    private static String user = "root";
    private static String pass = "mark";
    public static String name = "";
    public static Integer user_id = null;
    public String loginDb(String name, String password) {
        String sql = "SELECT * FROM users WHERE name = ?  AND pass = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                this.name = name;
                this.user_id = rs.getInt("user_id");
                return rs.getString("role");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
