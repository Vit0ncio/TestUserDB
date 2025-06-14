package br.testuserdb.dao;

import br.testuserdb.model.User;
import br.testuserdb.service.HashSHA256;

import javax.xml.transform.Result;
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
            String hashedPassword = HashSHA256.hashPassword(password);

            String sql = "select * from users where name = ? and password = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setString(2, hashedPassword);

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
                System.err.println("User not found. Password or Name incorrect.");
            }
        } catch (SQLException sqle) {
            System.err.println("Error in login: " + sqle.getMessage());
            return null;
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
            System.err.println("Error finding user: " + sqle.getMessage());
            return null;
        }
        return user;
    }

    public static User createUser(String name, String email, String password) {
        User user = null;
        Connection conn = ConnectDAO.connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "insert into users (name, email, password, role) values (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            String hashedPassword = HashSHA256.hashPassword(password);

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hashedPassword);
            stmt.setString(4, user.getRole());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException sqle) {
            System.err.println("Error creating user: " + sqle.getMessage());
        } finally {
            ConnectDAO.disconnectDB(conn, stmt, rs);
        }
        return user;
    }
}