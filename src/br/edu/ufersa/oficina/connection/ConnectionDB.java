package br.edu.ufersa.oficina.connection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String url = "jdbc:mysql://localhost:3306/mecdb";
    private static final String user = "root";
    private static final String password = "";

    private static Connection connection;

    public static Connection getConnection(){

        try {
            if (connection == null) {
                System.out.println("Conectado com Sucesso");
                connection = DriverManager.getConnection(url, user, password);
            }

        }

        catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return connection;
    }

}