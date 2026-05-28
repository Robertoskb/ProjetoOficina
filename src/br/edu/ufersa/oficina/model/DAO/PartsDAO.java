package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Factories.PartsFactory;
import br.edu.ufersa.oficina.model.connection.ConnectionDB;
import br.edu.ufersa.oficina.model.entity.Parts;
import java.sql.*;
import java.util.ArrayList;

public class PartsDAO extends GenericDAO<Parts> {

    public PartsDAO() {
        super("parts", new PartsFactory());
    }

    public Parts getPartById(int id) {
        return filterEntityById(id);
    }

    public ArrayList<Parts> getAllParts() {
        return getAllEntity();
    }

    private void register(String name, double price, String manufacturer, String model) {
        Connection conn = ConnectionDB.getConnection();
        if (conn == null) throw new MecException("Falha ao conectar ao banco de dados.");

        String sql = "INSERT INTO " + table + " (name, price, manufacturer, model) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, manufacturer);
            ps.setString(4, model);
            ps.execute();
        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public void addPart(Parts part) {
        register(part.getName(), part.getPrice(), part.getManufacturer(), part.getModel());
    }

    public void updatePart(Parts part) {
        Connection conn = ConnectionDB.getConnection();
        if (conn == null) throw new MecException("Falha ao conectar ao banco de dados.");

        String sql = "UPDATE " + table + " SET name = ?, price = ?, manufacturer = ?, model = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, part.getName());
            ps.setDouble(2, part.getPrice());
            ps.setString(3, part.getManufacturer());
            ps.setString(4, part.getModel());
            ps.setInt(5, part.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }
}
