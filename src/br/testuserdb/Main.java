package br.testuserdb;

import br.testuserdb.dao.ConnectDAO;

public class Main {
    public static void main(String[] args) {
        ConnectDAO connDAO = new ConnectDAO();

        connDAO.connectDB();
        connDAO.disconnectDB();
    }
}
