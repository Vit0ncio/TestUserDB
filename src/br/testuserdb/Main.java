package br.testuserdb;

import br.testuserdb.dao.UserDAO;
import br.testuserdb.model.User;

import java.util.Scanner;

public class Main {
    static User user;
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        login();
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
}