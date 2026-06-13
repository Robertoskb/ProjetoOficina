package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Factories.CarFactory;
import br.edu.ufersa.oficina.model.connection.ConnectionDB;
import br.edu.ufersa.oficina.model.entity.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarDAO extends GenericDAO<Car> {

    public CarDAO() {

        super("Car", new CarFactory());

    }

    public ArrayList<Car> getAllCar() {
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + table + " ca INNER JOIN Client cl ON ca.client_id = cl.client_id";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            return factory.createArrayEntity(rs);
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }
    
    public Car getCarById(int id) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT FROM " + table + " ca INNER JOIN Client cl ON ca.client_id = cl.client_id where c.car_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next())
                    return factory.createEntity(rs);
                return null;
            }
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public Car getCarByPlate(String plate) {

        return filterEntity("plate", plate);

    }

    public ArrayList<Car> getCarsByClientId(int id) {

        return filterArrayEntity("client_id", Integer.toString(id));

    }

    public void addCar(Car car) {
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

    public void updateCar(Car car) {
        Connection conn = ConnectionDB.getConnection();

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
            throw new MecException(e.getMessage());
        }
    }
}
