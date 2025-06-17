package br.testuserdb.dao;

import br.testuserdb.service.HashSHA256;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DataDAO {

    // Create the database file
    public void createDB() {
        Scanner scan = new Scanner(System.in);
        File database = new File("testuser.db");
        ConnectDAO connDAO = new ConnectDAO();

        try {
            if (!database.exists()) {
                // If the file doesn't exist, ask the user if he wants to create a new one
                System.out.println("Database not found. You want to create a new one? [Y/N]");
                String choice = scan.nextLine().trim().toLowerCase();

                switch (choice) {
                    case "y" -> {
                        if (database.getParentFile() != null) {
                            // If the directory db doesn't exist, create a new one
                            database.getParentFile().mkdirs();
                        }

                        boolean created = database.createNewFile();

                        if (created) {
                            System.out.println("File created.");
                            insertDB();
                        } else {
                            System.err.println("File could not be created.");
                        }
                    }

                    case "n" -> {
                        // If the user doesn't want to create the database, go to connect to the database
                        // SQLite creates a new one when connecting, in the project root folder
                        break;
                    }

                    default -> System.out.println("Choice a valid option.");
                }
            }
        } catch (IOException ioe) {
            System.err.println("A error occurred. " + ioe.getMessage());
        }
        connDAO.connectDB();
    }

    public void deleteDB() {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;
        File database = new File("testuser.db");

        ConnectDAO.disconnectDB(conn, stmt, rs);

        if (database.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.err.println("File could not be deleted.");
        }
    }

    public void insertDB() {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ConnectDAO connDAO = new ConnectDAO();
        Connection conn = ConnectDAO.connectDB();

        // Create the table
        String createTableSQL = "create table if not exists users (" +
                        "id integer primary key autoincrement not null," +
                        "name text not null," +
                        "email text not null," +
                        "password text not null," +
                        "role text not null" +
                     ");";

        // Prepare the update in the sql string
        try {
            stmt = conn.prepareStatement(createTableSQL);
            stmt.execute(); // Execute the sql string
            System.out.println("Table created.");
        } catch (SQLException sqle) {
            System.err.println("Error creating the table: " + sqle.getMessage());
        }

        // Inserting data
        String[] names = {"John", "Maria", "Adam"};
        String[] emails = {"john@email.org", "maria@email.org", "adam@email.org"};
        String[] passwords = {"john123", "maria123", "adam123"};
        String[] roles =  {"User", "User", "Admin"};

        // Insert data in the table
        String insertSQL = "insert into users(name, email, password, role) values(?,?,?,?)";

        try {
            stmt = conn.prepareStatement(insertSQL);

            // Made a for loop to insert the users
            // Why does SQLite work like this? I don't know
            // Adds users up to the limit of names entered
            for (int i = 0; i < names.length; i++) {
                String hashedPassword = HashSHA256.hashPassword(passwords[i]);
                stmt.setString(1, names[i]);
                stmt.setString(2, emails[i]);
                stmt.setString(3, hashedPassword);
                stmt.setString(4, roles[i]);
                stmt.executeUpdate();
            }
            System.out.println("Users created.");
        } catch (SQLException sqle) {
            System.err.println("Insert error: " + sqle.getMessage());
        }
        // Disconnect from the database
        connDAO.disconnectDB(conn, stmt, rs);
    }
}