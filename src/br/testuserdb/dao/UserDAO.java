package br.testuserdb.dao;

import br.testuserdb.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    ConnectDAO connDAO = new ConnectDAO();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public User login(String name, String password) {
        try {
            conn = connDAO.connectDB();
            String sql = "select * from users where name = ? and password = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                return user;
            }
            return null;
        } catch (SQLException sqle) {
            System.out.println("Error in login: " + sqle.getMessage());
        } finally {
            connDAO.disconnectDB();
        }
        return null;
    }
}