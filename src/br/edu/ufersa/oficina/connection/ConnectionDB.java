package br.edu.ufersa.oficina.connection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String url = "jdbc:mysql://localhost:3306/oficina_db";
    private static final String user = "root";
    private static final String password = "";

    private static Connection connection;

    public static Connection getConnection(){

        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Conectado com Sucesso");
            }

        }

        catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return connection;
    }

}