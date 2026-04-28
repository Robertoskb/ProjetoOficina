package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class ServiceFactory{
    public static Service createService(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");

        return new Service(id, name, price);
    }

    public static ArrayList<Service> createArrayServices(ResultSet rs) throws SQLException{
        ArrayList<Service> services = new ArrayList<Service>();

        while (rs.next())
            services.add(createService(rs));

        return services;
    }
}
public class ServiceDAO extends GenericDAO {
    public ServiceDAO() {
        super("service");
    }

    public ArrayList<Service> getAllService(){
        ResultSet rs = getALl();

        if (rs == null) return null;

        try {
            return ServiceFactory.createArrayServices(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    private void register(String name, double price) {
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

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

        if (conn == null) return;

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

    public Service getServiceBy(int id) {
        try (ResultSet rs = this.filterById(id)) {
            if (rs != null && rs.next())
                return ServiceFactory.createService(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public ArrayList<Service> getServiceByName(String name) {
        try (ResultSet rs = this.filter("name", name)) {
            if (rs != null && rs.next())
                return ServiceFactory.createArrayServices(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}