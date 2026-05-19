package br.edu.ufersa.oficina.Factories;

import br.edu.ufersa.oficina.DAO.ClientDAO;
import br.edu.ufersa.oficina.entity.Car;
import br.edu.ufersa.oficina.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarFactory {
    public static Car createCar(ResultSet rs) throws SQLException {
        Client client = new ClientDAO().getClientById(rs.getInt("client_id"));
        int id = rs.getInt("id");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        String color = rs.getString("color");
        String plate = rs.getString("plate");
        int year = rs.getInt("year");
        int mileage = rs.getInt("mileage");

        return new Car(id, brand, model, color, plate, year, mileage, client);
    }

    public static ArrayList<Car> createArrayCars(ResultSet rs) throws SQLException {
        ArrayList<Car> cars = new ArrayList<Car>();
        while (rs.next()) {
            cars.add(createCar(rs));
        }
        return cars;
    }
}
