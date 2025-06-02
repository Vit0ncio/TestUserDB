package br.testuserdb.dao;

import java.sql.*;

public class ConnectDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void connectDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:testuser.db");
            System.out.println("Connection successful.");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public void disconnectDB() {
        try {
            rs.close();
        } catch (SQLException e) {
        }
        try {
            ps.close();
        } catch (SQLException e) {
        }
        try {
            conn.close();
        } catch (SQLException e) {
        }
    }
}
