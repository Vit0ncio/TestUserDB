package br.testuserdb.dao;

import br.testuserdb.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static User login(String name, String password) {
        User user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDAO.connectDB();
            String sql = "select * from users where name = ? and password = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            } else {
                System.out.println("User not found. Password or Name incorrect.");
            }
        } catch (SQLException sqle) {
            System.out.println("Error in login: " + sqle.getMessage());
        } finally {
            ConnectDAO.disconnectDB(conn, stmt, rs);
        }
        return user;
    }

    public static User readUser(String name) {
        User user = null;
        Connection conn;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDAO.connectDB();
            String sql = "select * from users where name = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);

            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                name = rs.getString("name");
                String email = rs.getString("email");

                System.out.println("ID: " + id + "\n" + "Name: " + name + "\n" + "Email: " + email);
            }
        } catch (SQLException sqle) {
            System.out.println("Error finding user: " + sqle.getMessage());
        }
        return user;
    }
}