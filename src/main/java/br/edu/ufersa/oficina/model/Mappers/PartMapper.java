package br.edu.ufersa.oficina.model.Mappers;

import br.edu.ufersa.oficina.model.Entity.Part;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PartMapper implements GenericMapper<Part> {
    public Part createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("part_id");
        String name = rs.getString("part_name");
        double price = rs.getDouble("part_price");
        String manufacturer = rs.getString("manufacturer");
        String model = rs.getString("model");

        return new Part(id, name, price, manufacturer, model);
    }

    public ArrayList<Part> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Part> parts = new ArrayList<>();
        while (rs.next()) 
            parts.add(createEntity(rs));
        return parts;
    }
}
