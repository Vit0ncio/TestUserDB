package br.testuserdb;

import br.testuserdb.dao.DataDAO;

public class Main {
    public static void main(String[] args) {
        // Tests and more tests, remove them
        // The main file, responsible for executing the project
        DataDAO dataDAO = new DataDAO();
        dataDAO.createDB();
    }
}