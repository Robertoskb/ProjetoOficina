package br.edu.ufersa.oficina.model.Mappers;

import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarMapper implements GenericMapper<Car> {
    public Car createEntity(ResultSet rs) throws SQLException {
        Client client = new ClientMapper().createEntity(rs);
        int id = rs.getInt("car_id");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        String color = rs.getString("color");
        String plate = rs.getString("plate");
        int year = rs.getInt("year");
        int mileage = rs.getInt("mileage");

        return new Car(id, brand, model, color, plate, year, mileage, client);
    }

    public ArrayList<Car> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Car> cars = new ArrayList<>();
        while (rs.next())
            cars.add(createEntity(rs));
        return cars;
    }
}
