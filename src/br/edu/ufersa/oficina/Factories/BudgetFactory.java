package br.edu.ufersa.oficina.Factories;

import br.edu.ufersa.oficina.DAO.CarDAO;
import br.edu.ufersa.oficina.entity.Budget;
import br.edu.ufersa.oficina.entity.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BudgetFactory implements GenericFactory<Budget> {
    public Budget createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Car car = new CarDAO().getCarById(rs.getInt("car_id"));
        double price = rs.getDouble("price");
        LocalDate date_start = rs.getDate("date_start").toLocalDate();
        LocalDate date_finish = rs.getDate("date_finish").toLocalDate();

        return new Budget(id, null, null, car, price, date_start, date_finish);
    }

    public ArrayList<Budget> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Budget> budgets = new ArrayList<Budget>();

        while (rs.next())
            budgets.add(createEntity(rs));

        return budgets;
    }
}
