package br.testuserdb;

import br.testuserdb.dao.DataDAO;
import br.testuserdb.dao.UserDAO;
import br.testuserdb.model.User;

import java.util.Scanner;

public class Main {
    User user
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        login();
    }
    
    public static void login() {
        System.out.println("TestUserDB - LOGIN");
        System.out.println("Enter your name");
        String name = scan.nextLine();
        System.out.println("Enter your password");
        String password = scan.nextLine();

        User user = UserDAO.login(name, password);
    }
}