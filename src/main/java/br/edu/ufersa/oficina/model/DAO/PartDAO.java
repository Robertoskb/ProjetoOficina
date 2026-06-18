package br.edu.ufersa.oficina.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Mappers.PartsMapper;

public class PartDAO extends GenericDAO<Part> {

    public PartDAO() {

        super("Part", new PartsMapper());

    }

    public Part getPartById(int id) {

        return filterEntityById(id);

    }

    public ArrayList<Part> getAllParts() {

        return getAllEntities();

    }

    public ArrayList<Part> getPartsByName(String name){

        return filterArrayEntity("part_name", name);

    }

    public ArrayList<Part> getPartsByManufacturer(String manufacturer){

        return filterArrayEntity("manufacturer", manufacturer);

    }

    public ArrayList<Part> getPartsByModel(String model){

        return filterArrayEntity("model", model);

    }

    private void register(String name, double price, String manufacturer, String model) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + table + " (part_name, part_price, manufacturer, model) VALUES (?, ?, ?, ?)";

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

    public void insert(Part part) {
        register(part.getName(), part.getPrice(), part.getManufacturer(), part.getModel());
    }

    public void update(Part part) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "UPDATE " + table + " SET part_name = ?, part_price = ?, manufacturer = ?, model = ? WHERE part_id = ?";

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
