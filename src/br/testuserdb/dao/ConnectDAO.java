package br.testuserdb.dao;

import java.sql.*;

public class ConnectDAO {
    static Connection conn = null;
    static PreparedStatement stmt = null;
    static ResultSet rs = null;

    public static Connection connectDB() {
        try {
            // Get the URL to database
            conn = DriverManager.getConnection("jdbc:sqlite:db/testuser.db");
            System.out.println("Connection successful.");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return conn;
    }

    public static void disconnectDB() {
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
