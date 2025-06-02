package br.testuserdb.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DataDAO {
    ConnectDAO connDAO = new ConnectDAO();
    File database = new File("db/testuser.db");
    Scanner scan = new Scanner(System.in);
    PreparedStatement stmt = null;

    // Create the database file
    public void createDB() {
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
                        } else {
                            System.out.println("File could not be created.");
                        }
                        break;
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
            System.out.println("A error occurred. " + ioe.getMessage());
        }
        connDAO.connectDB();
    }

    public void deleteDB() {
        if (database.exists()) {
            boolean deleted = database.delete();

            if (deleted) {
                System.out.println("Database deleted successfully.");
            } else {
                System.out.println("Failed to delete the database.");
            }
        }
    }

    public void insertDB() {
        Connection conn = connDAO.connectDB();

        // Create the table
        String createTableSQL = "create table if not exists users (" +
                        "id integer primary key autoincrement not null," +
                        "name text not null," +
                        "email text not null," +
                        "password text not null," +
                        "role text not null" +
                     ");";

        // Prepare the update in the sql string
        try (PreparedStatement stmt = conn.prepareStatement(createTableSQL)){
            stmt.execute(); // Execute the sql string
            System.out.println("Table created.");
        } catch (SQLException sqle) {
            System.out.println("Error create table: " + sqle.getMessage());
        }

        // Inserting data
        String[] names = {"John", "Maria", "Adam"};
        String[] emails = {"john@email.org", "maria@email.org", "adam@email.org"};
        String[] passwords = {"john123", "maria123", "adam123"};
        String[] roles =  {"User", "User", "Admin"};

        // Insert data in the table
        String insertSQL = "insert into users(name, email, password, role) values(?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            // Made a for loop to insert the users
            // Why does SQLite work like this? I don't know
            // Adds users up to the limit of names entered
            for (int i = 0; i < names.length; i++) {
                stmt.setString(1, names[i]);
                stmt.setString(2, emails[i]);
                stmt.setString(3, passwords[i]);
                stmt.setString(4, roles[i]);
                stmt.executeUpdate();
            }
            System.out.println("Users created.");
        } catch (SQLException sqle) {
            System.out.println("Insert error: " + sqle.getMessage());
        }
        // Disconnect from the database
        connDAO.disconnectDB();
    }
}