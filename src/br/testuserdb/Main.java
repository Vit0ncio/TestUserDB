package br.testuserdb;

import br.testuserdb.dao.DataDAO;
import br.testuserdb.dao.UserDAO;
import br.testuserdb.model.User;

import java.util.Scanner;

public class Main {
    static User user;
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        DataDAO dataDAO = new DataDAO();
        dataDAO.createDB();
        login();
        menu();
    }

    public static void menu() {
        System.out.println("What do you want to do?");
        System.out.println("1 - See other users");
        System.out.println("0 - Exit");
        byte choice = scan.nextByte();

        scan.nextLine();

        switch (choice) {
            case 1 -> {
                readUser();
            }

            case 0 -> {
                System.out.println("Bye bye!");
                break;
            }

            default -> System.out.println("Invalid option. Try Again.");
        }
    }
    
    public static boolean login() {
        System.out.println("TestUserDB - LOGIN");
        System.out.println("Enter your name");
        String name = scan.nextLine();
        System.out.println("Enter your password");
        String password = scan.nextLine();

        user = UserDAO.login(name, password);

        if (user != null) {
            System.out.println("Welcome " + user.getName() + "!");
            return true;
        } else {
            System.out.println("Invalid login. Try Again.");
            return false;
        }
    }

    public static void readUser() {
        System.out.println("Enter a name:");
        String name = scan.nextLine();

        user = UserDAO.readUser(name);
        menu();
    }
}