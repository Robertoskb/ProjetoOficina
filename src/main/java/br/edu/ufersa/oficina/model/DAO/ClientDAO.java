package br.edu.ufersa.oficina.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Mappers.ClientMapper;

public class ClientDAO extends GenericDAO<Client> {

    public ClientDAO(){
        super("Client", new ClientMapper());
    }

    public Client getClientById(int id) {
        return filterEntityById(id);
    }

    public ArrayList<Client> getAllClients() { return getAllEntities(); }

    private void register(String name, String address, long cpf){
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + table + " (client_name, address, cpf) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setLong(3, cpf);

            ps.execute();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public void insert(Client client){
        register(client.getName(), client.getAddress(), client.getCPF());
    }

    public void update(Client client){
        Connection conn = ConnectionDB.getConnection();

        String sql = "UPDATE " + table + " SET client_name = ?, address = ?, cpf = ? WHERE client_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setLong(3, client.getCPF());

            ps.setInt(4, client.getId());

            ps.execute();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }


}