package br.testuserdb.dao;

import java.sql.*;

public class ConnectDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void connectDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:testuser.db", "root", "");
            System.out.println("Connection successful.");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public void disconnectDB() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqle) { }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException sqle) { }
        }

        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) { }
        }
    }
}
