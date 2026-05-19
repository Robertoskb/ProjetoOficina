package br.edu.ufersa.oficina.Factories;

import br.edu.ufersa.oficina.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientFactory {
    public static Client createClient(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String address = rs.getNString("address");
        long cpf = rs.getLong("cpf");

        return new Client(id, name, address, cpf);

    }

    public static ArrayList<Client> createArrayClients(ResultSet rs) throws SQLException {
        ArrayList<Client> clients = new ArrayList<Client>();

        while (rs.next())
            clients.add(createClient(rs));

        return clients;
    }
}
