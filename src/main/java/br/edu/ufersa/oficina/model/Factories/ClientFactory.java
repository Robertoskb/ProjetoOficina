package br.edu.ufersa.oficina.model.Factories;

import br.edu.ufersa.oficina.model.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientFactory implements GenericFactory<Client> {
    public Client createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("client_id");
        String name = rs.getString("client_name");
        String address = rs.getNString("address");
        long cpf = rs.getLong("cpf");

        return new Client(id, name, address, cpf);

    }

    public ArrayList<Client> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Client> clients = new ArrayList<Client>();

        while (rs.next())
            clients.add(createEntity(rs));

        return clients;
    }
}
