package br.edu.ufersa.oficina.model.Factories;

import br.edu.ufersa.oficina.model.entity.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceFactory implements GenericFactory<Service> {
    public Service createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("service_id");
        String name = rs.getString("service_name");
        double price = rs.getDouble("service_price");

        return new Service(id, name, price);
    }

    public ArrayList<Service> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Service> services = new ArrayList<Service>();

        while (rs.next())
            services.add(createEntity(rs));

        return services;
    }
}
