package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Factories.ClientFactory;
import br.edu.ufersa.oficina.model.connection.ConnectionDB;
import br.edu.ufersa.oficina.model.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO extends GenericDAO<Client> {

    public ClientDAO(){
        super("Client", new ClientFactory());
    }

    public Client getClientById(int id) {
        return filterEntityById(id);
    }

    public ArrayList<Client> getAllClient() { return getAllEntity(); }

    private void register(String name, String address, long cpf){
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + table + " (name, address, cpf) VALUES (?, ?, ?)";

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

    public void addClient(Client client){
        register(client.getName(), client.getAddress(), client.getCPF());
    }

    public void addClient(String name, String address, long cpf){
        register(name, address, cpf);
    }

    public void update(int id, String name, String address, long cpf){
        Connection conn = ConnectionDB.getConnection();

        String sql = "UPDATE " + table + " SET client_name = ?, address = ?, cpf = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setLong(3, cpf);

            ps.setInt(4, id);

            ps.execute();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }


}