package br.edu.ufersa.oficina.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.Service;
import br.edu.ufersa.oficina.model.Mappers.ServiceMapper;

public class ServiceDAO extends GenericDAO<Service> {
    public ServiceDAO() {
        super("Service", new ServiceMapper());
    }

    public ArrayList<Service> getAllServices() { return getAllEntities(); }
    private void register(String name, double price) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + this.table + " (service_name, service_price) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.execute();
        } catch (Exception e) {
            throw new MecException(e.getMessage());
        }

    }

    public void update(Service service) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "UPDATE " + this.table + " SET service_name = ?, service_price = ? WHERE service_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, service.getName());
            ps.setDouble(2, service.getPrice());
            ps.setInt(3, service.getId());
            ps.execute();
        } catch (Exception e) {
            throw new MecException(e.getMessage());
        }

    }

    public void insert(Service service) {
        this.register(service.getName(), service.getPrice());
    }

    public void insert(String name, float price) {
        this.register(name, price);
    }

    public Service getServiceById(int id) { return filterEntityById(id); }

    public ArrayList<Service> getServiceByName(String name)  {
        return filterArrayEntity("service_name", name);
    }
}