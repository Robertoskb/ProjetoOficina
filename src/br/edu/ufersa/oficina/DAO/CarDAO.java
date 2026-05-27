package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.Factories.CarFactory;
import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarDAO extends GenericDAO<Car> {

    public CarDAO() {
        super("car", new CarFactory());
    }

    public ArrayList<Car> getAllCar() {
        return getAllEntity();
    }
    
    public Car getCarById(int id) {
        return filterEntityById(id);
    }

    public Car getCarByPlate(String plate) {
        return filterEntity("plate", plate);
    }

    public ArrayList<Car> getCarsByClientId(int id) {
        Connection conn = ConnectionDB.getConnection();
        if (conn == null) return new ArrayList<>();

        String sql = "SELECT * FROM " + table + " WHERE client_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return factory.createArrayEntity(rs);
        }catch (SQLException e){
            System.out.pritLn(e.getMessage());
            return new ArrayList<>();
        }
        
    }

    public void addCar(Car car) {
        Connection conn = ConnectionDB.getConnection();
        if (conn == null) return;

        String sql = "INSERT INTO " + table + " (client_id, brand, model, color, plate, `year`, mileage) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, car.getClient().getId());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setString(4, car.getColor());
            ps.setString(5, car.getPlate());
            ps.setInt(6, car.getYear());
            ps.setInt(7, car.getMileage());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCar(Car car) {
        Connection conn = ConnectionDB.getConnection();
        if (conn == null) return;

        String sql = "UPDATE " + table + " SET client_id = ?, brand = ?, model = ?, color = ?, plate = ?, `year` = ?, mileage = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, car.getClient().getId());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setString(4, car.getColor());
            ps.setString(5, car.getPlate());
            ps.setInt(6, car.getYear());
            ps.setInt(7, car.getMileage());
            ps.setInt(8, car.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
