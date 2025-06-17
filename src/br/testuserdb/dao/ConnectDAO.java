package br.testuserdb.dao;

import java.sql.*;

public class ConnectDAO {
    public static Connection connectDB() {
        Connection conn = null;
        try {
            // Get the URL to database
            conn = DriverManager.getConnection("jdbc:sqlite:testuser.db");
            System.out.println("Connection successful.");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return conn;
    }

    public static void disconnectDB(Connection conn, PreparedStatement stmt, ResultSet rs) {
        // Close the connection with the database
        // Exactly in this order: ResultSet, PreparedStatement and Connection
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }

            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
        } finally {
            System.out.println("Disconnection successful.");
        }
    }
}
