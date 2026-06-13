package br.edu.ufersa.oficina.model.Factories;

import br.edu.ufersa.oficina.model.DAO.CarDAO;
import br.edu.ufersa.oficina.model.entity.Car;
import br.edu.ufersa.oficina.model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderFactory implements GenericFactory<Order> {
    public Order createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("order_id");
        Car car = new CarFactory().createEntity(rs);
        double price = rs.getDouble("order_price");
        LocalDate date_start = rs.getDate("order_date_start").toLocalDate();
        LocalDate date_finish = rs.getDate("order_date_finish").toLocalDate();
        boolean completed = rs.getBoolean("completed");

        return new Order(id, null, null, car, price, date_start, date_finish, completed);
    }

    public ArrayList<Order> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        while (rs.next())
            orders.add(createEntity(rs));

        return orders;
    }
}
