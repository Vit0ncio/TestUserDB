package br.testuserdb.dao;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DataDAO {
    ConnectDAO connDAO = new ConnectDAO();
    File database = new File("db/testuser.db");
    Scanner scan = new Scanner(System.in);

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
}