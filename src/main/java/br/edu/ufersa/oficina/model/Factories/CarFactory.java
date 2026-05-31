package br.edu.ufersa.oficina.model.Factories;

import br.edu.ufersa.oficina.model.DAO.ClientDAO;
import br.edu.ufersa.oficina.model.entity.Car;
import br.edu.ufersa.oficina.model.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarFactory implements GenericFactory<Car> {
    public Car createEntity(ResultSet rs) throws SQLException {
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

    public ArrayList<Car> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Car> cars = new ArrayList<>();
        while (rs.next())
            cars.add(createEntity(rs));
        return cars;
    }
}
