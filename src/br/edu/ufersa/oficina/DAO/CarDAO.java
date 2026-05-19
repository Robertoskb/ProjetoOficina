package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.Factories.CarFactory;
import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarDAO extends GenericDAO {

    public CarDAO() {
        super("car");
    }

    public ArrayList<Car> getAllCar() {
        ResultSet rs = getAll();
        if (rs == null) return new ArrayList<>();
        try{
            return CarFactory.createArrayCars(rs);
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public Car getCarById(int id) {
        try (ResultSet rs = filterById(id)) {
            if (rs != null && rs.next()) {
                return CarFactory.createCar(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Car getCarByPlate(String plate) {
        try (ResultSet rs = filter("plate", plate)) {
            if (rs != null && rs.next()) {
                return CarFactory.createCar(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Car> getCarsByClientId(int id) {
        try (ResultSet rs = filter("client_id", Integer.toString(id))) {
            if (rs == null) return new ArrayList<>();
            return CarFactory.createArrayCars(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
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
