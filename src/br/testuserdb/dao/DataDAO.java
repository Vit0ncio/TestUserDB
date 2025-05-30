package br.testuserdb.dao;

import java.sql.*;

public class DataDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private void connectDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:testuser.db", "root", "");
            System.out.println("Connection successful.");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    private void disconnectDB() {
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
            } catch (SQLException e) { }
        }
    }
}
