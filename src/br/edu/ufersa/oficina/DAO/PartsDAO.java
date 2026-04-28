package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.Parts;
import java.sql.*;
import java.util.ArrayList;

class PartsFactory {
    public static Parts createPart(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        String manufacturer = rs.getString("manufacturer");
        String model = rs.getString("model");

        return new Parts(id, name, price, manufacturer, model);
    }

    public static ArrayList<Parts> createArrayParts(ResultSet rs) throws SQLException {
        ArrayList<Parts> parts = new ArrayList<>();
        while (rs.next()) {
            parts.add(createPart(rs));
        }
        return parts;
    }
}

public class PartsDAO extends GenericDAO {

    public PartsDAO() {
        super("parts");
    }

    public Parts getPartById(int id) {
        try (ResultSet rs = filterById(id)) {
            if (rs != null && rs.next()) {
                return PartsFactory.createPart(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Parts> getAllParts() {
        ResultSet rs = getALl();
        if (rs == null) return null;

        try {
            return PartsFactory.createArrayParts(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void register(String name, double price, String manufacturer, String model) {
        Connection conn = ConnectionDB.getConnection();
        if (conn == null) return;

        String sql = "INSERT INTO " + table + " (name, price, manufacturer, model) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, manufacturer);
            ps.setString(4, model);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPart(Parts part) {
        register(part.getName(), part.getPrice(), part.getManufacturer(), part.getModel());
    }

    public void updatePart(Parts part) {
        Connection conn = ConnectionDB.getConnection();
        if (conn == null) return;

        String sql = "UPDATE " + table + " SET name = ?, price = ?, manufacturer = ?, model = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, part.getName());
            ps.setDouble(2, part.getPrice());
            ps.setString(3, part.getManufacturer());
            ps.setString(4, part.getModel());
            ps.setInt(5, part.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
