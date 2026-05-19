package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.Factories.ClientFactory;
import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO extends GenericDAO{

    public ClientDAO(){
        super("client");
    }



    public Client getClientById(int id){
        try (ResultSet rs = filterById(id)){
            if (rs != null && rs.next())
                return ClientFactory.createClient(rs);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

        }
        return null;
    }

    public ArrayList<Client> getAllClient(){
        ResultSet rs = getALl();

        if (rs == null) return null;

        try {
            return ClientFactory.createArrayClients(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void register(String name, String address, long cpf){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

        String sql = "INSERT INTO " + table + " (name, address, cpf) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setLong(3, cpf);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());
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

        if (conn == null) return;

        String sql = "UPDATE " + table + " SET name = ?, andress = ?, cpf = ? WHERE id ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setLong(3, cpf);

            ps.setInt(4, id);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


}