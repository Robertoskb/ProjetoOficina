package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.model.Factories.ServiceFactory;
import br.edu.ufersa.oficina.model.connection.ConnectionDB;
import br.edu.ufersa.oficina.model.entity.Parts;
import br.edu.ufersa.oficina.model.entity.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDAO extends GenericDAO<Service> {
    public ServiceDAO() {
        super("service", new ServiceFactory());
    }

    public ArrayList<Service> getAllService() { return getAllEntity(); }
    private void register(String name, double price) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + this.table + " (name, proce) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void update(Service service) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "UPDATE TABLE " + this.table + " SET name = ?, price = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, service.getName());
            ps.setDouble(2, service.getPrice());
            ps.setInt(3, service.getId());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void addService(Service service) {
        this.register(service.getName(), service.getPrice());
    }

    public void addService(String name, float price) {
        this.register(name, price);
    }

    public Service getServiceById(int id) { return filterEntityById(id); }

    public ArrayList<Service> getServiceByName(String name)  {
        return filterArrayEntity("name", name);
    }
}