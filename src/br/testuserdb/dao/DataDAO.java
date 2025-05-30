package br.testuserdb.dao;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DataDAO {
    ConnectDAO connDAO = new ConnectDAO();

    public void createDB() {
        try {
            File file = new File("db/testuser.db");
            Scanner scan = new Scanner(System.in);

            if (!file.exists()) {
                System.out.println("Database not found. You want to create a new one? [Y/N]");
                String choice = scan.nextLine().trim().toLowerCase();

                switch (choice) {
                    case "y":
                        file.createNewFile();
                        System.out.println("File created.");
                        break;

                    case "n":
                        connDAO.connectDB();
                        break;

                    default:
                        System.out.println("Choice a valid option.");
                }
            }
        } catch (IOException ioe) {
            System.out.println("A error occurred.");
            System.out.println(ioe.getMessage());
        }
    }
}