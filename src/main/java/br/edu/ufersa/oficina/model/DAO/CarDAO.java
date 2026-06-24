package br.edu.ufersa.oficina.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Mappers.CarMapper;

public class CarDAO extends GenericDAO<Car> {

    public CarDAO() {
        super("Car", new CarMapper());
    }

    public ArrayList<Car> getAllCars() {
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + table + " ca LEFT JOIN Client cl ON ca.client_id = cl.client_id ORDER BY 1 DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            return mapper.createArrayEntity(rs);
        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public Car getCarById(int id) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + table + " ca LEFT JOIN Client cl ON ca.client_id = cl.client_id WHERE ca.car_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return mapper.createEntity(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public Car getCarByPlate(String plate) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + table + " ca LEFT JOIN Client cl ON ca.client_id = cl.client_id WHERE ca.plate = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return mapper.createEntity(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<Car> getCarsByClientId(int id) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + table + " ca LEFT JOIN Client cl ON ca.client_id = cl.client_id WHERE ca.client_id = ? ORDER BY 1 DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return mapper.createArrayEntity(rs);
            }
        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public void insert(Car car) {
        Connection conn = ConnectionDB.getConnection();

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
            throw new MecException(e.getMessage());
        }
    }

    public void update(Car car) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "UPDATE " + table + " SET client_id = ?, brand = ?, model = ?, color = ?, plate = ?, `year` = ?, mileage = ? WHERE car_id = ?";

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
            throw new MecException(e.getMessage());
        }
    }
}