package br.testuserdb;

import br.testuserdb.dao.DataDAO;

public class Main {
    public static void main(String[] args) {
        DataDAO dataDAO = new DataDAO();

        dataDAO.connectDB();
    }
}
