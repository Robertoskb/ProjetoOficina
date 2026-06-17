package br.edu.ufersa.oficina.model.Mappers;

import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Order;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderMapper implements GenericMapper<Order> {
    public Order createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("order_id");
        Car car = new CarMapper().createEntity(rs);
        double price = rs.getDouble("order_price");
        Date date_start = rs.getDate("order_date_start");
        Date date_finish = rs.getDate("order_date_finish");
        boolean completed = rs.getBoolean("completed");

        LocalDate start = null, finish = null;
        if (date_start != null)
            start = date_start.toLocalDate();
        if (date_finish != null)
            finish = date_finish.toLocalDate();

        return new Order(id, null, null, car, price, start, finish, completed);
    }

    public ArrayList<Order> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        while (rs.next())
            orders.add(createEntity(rs));

        return orders;
    }
}
