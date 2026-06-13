package br.edu.ufersa.oficina.model.Factories;

import br.edu.ufersa.oficina.model.DAO.CarDAO;
import br.edu.ufersa.oficina.model.entity.Budget;
import br.edu.ufersa.oficina.model.entity.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BudgetFactory implements GenericFactory<Budget> {
    public Budget createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("budget_id");
        Car car = new CarFactory().createEntity(rs);
        double price = rs.getDouble("budget_price");
        LocalDate date_start = rs.getDate("budget_date_start").toLocalDate();
        LocalDate date_finish = rs.getDate("budget_date_finish").toLocalDate();

        return new Budget(id, null, null, car, price, date_start, date_finish);
    }

    public ArrayList<Budget> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Budget> budgets = new ArrayList<Budget>();

        while (rs.next())
            budgets.add(createEntity(rs));

        return budgets;
    }
}
