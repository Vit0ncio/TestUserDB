package br.testuserdb.dao;

import java.sql.*;

public class ConnectDAO {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public Connection connectDB() {
        try {
            // Get the URL to database
            conn = DriverManager.getConnection("jdbc:sqlite:db/testuser.db");
            System.out.println("Connection successful.");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return conn;
    }

    public void disconnectDB() {
        // Close the connection with the database
        // Exactly in this order: ResultSet, PreparedStatement and Connection
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
        }
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
        }
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
        }
    }
}
