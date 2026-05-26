package br.edu.ufersa.oficina.Factories;

import br.edu.ufersa.oficina.entity.Parts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PartsFactory implements GenericFactory<Parts> {
    public Parts createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        String manufacturer = rs.getString("manufacturer");
        String model = rs.getString("model");

        return new Parts(id, name, price, manufacturer, model);
    }

    public ArrayList<Parts> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Parts> parts = new ArrayList<>();
        while (rs.next()) 
            parts.add(createEntity(rs));
        return parts;
    }
}
