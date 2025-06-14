package br.testuserdb;

import br.testuserdb.dao.DataDAO;
import br.testuserdb.dao.UserDAO;
import br.testuserdb.model.User;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DataDAO dataDAO = new DataDAO();
        dataDAO.createDB();
        login();
        menu();
    }

    public static void menu() {
        Scanner scan = new Scanner(System.in);
        DataDAO dataDAO = new DataDAO();

        System.out.println("What do you want to do?");
        System.out.println("1 - See other users");
        System.out.println("2 - Delete Database");
        System.out.println("0 - Exit");
        byte choice = scan.nextByte();

        scan.nextLine();

        switch (choice) {
            case 1 -> {
                readUser();
            }

            case 2 -> {
                dataDAO.deleteDB();
            }

            case 0 -> {
                System.out.println("Bye bye!");
                break;
            }

            default -> System.out.println("Invalid option. Try Again.");
        }
    }
    
    public static boolean login() {
        User user;
        Scanner scan = new Scanner(System.in);

        System.out.println("If you don't have a account, type 0 (zero) in name field");
        System.out.println("TestUserDB - LOGIN");
        System.out.println("Enter your name");
        String name = scan.nextLine();

        if (Objects.equals(name, "0")) {
            createUser();
        }

        System.out.println("Enter your password");
        String password = scan.nextLine();

        user = UserDAO.login(name, password);

        if (user != null) {
            System.out.println("Welcome " + user.getName() + "!");
            return true;
        } else {
            System.err.println("Invalid login. Try Again.");
            return false;
        }
    }

    public static void readUser() {
        Scanner scan = new Scanner(System.in);
        User user;

        System.out.println("Enter a name:");
        String name = scan.nextLine();

        user = UserDAO.readUser(name);
        menu();
    }

    public static void createUser() {
        Scanner scan = new Scanner(System.in);
        User user;

        System.out.println("Enter a name:");
        String name = scan.nextLine();
        System.out.println("Enter a email:");
        String email = scan.nextLine();
        System.out.println("Enter a password");
        String password = scan.nextLine();

        user = UserDAO.createUser(name, email, password);
        menu();
    }
}