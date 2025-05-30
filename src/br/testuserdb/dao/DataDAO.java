package br.testuserdb.dao;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DataDAO {
    ConnectDAO connDAO = new ConnectDAO();

    public void createDB() {
        File file = new File("db/testuser.db");
        Scanner scan = new Scanner(System.in);

        try {
            if (!file.exists()) {
                System.out.println("Database not found. You want to create a new one? [Y/N]");
                String choice = scan.nextLine().trim().toLowerCase();

                switch (choice) {
                    case "y" -> {
                        if (file.getParentFile() != null) {
                            file.getParentFile().mkdirs();
                        }

                        boolean created = file.createNewFile();

                        if (created) {
                            System.out.println("File created.");
                        } else {
                            System.out.println("File could not be created.");
                        }

                        break;
                    }

                    case "n" -> {
                        connDAO.connectDB();
                        break;
                    }

                    default -> System.out.println("Choice a valid option.");
                }
            }
        } catch (IOException ioe) {
            System.out.println("A error occurred.");
            System.out.println(ioe.getMessage());
        }
    }
}