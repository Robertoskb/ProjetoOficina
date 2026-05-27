package br.edu.ufersa.oficina.Factories;

import br.edu.ufersa.oficina.entity.Client;
import br.edu.ufersa.oficina.entity.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceFactory implements GenericFactory<Service> {
    public Service createService(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");

        return new Service(id, name, price);
    }

    public ArrayList<Service> createArrayServices(ResultSet rs) throws SQLException {
        ArrayList<Service> services = new ArrayList<Service>();

        while (rs.next())
            services.add(createService(rs));

        return services;
    }
}
