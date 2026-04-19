package br.edu.ufersa.oficina;

import br.edu.ufersa.oficina.DAO.UserDAO;
import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.User;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = ConnectionDB.getConnection();

        UserDAO userDao = new UserDAO();

        User user = new User("Roberto", "email@email.com", "123");
        userDao.addUser(user);
    }
}
