package br.edu.ufersa.oficina.model.Mappers;

import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Entity.Car;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class BudgetMapper implements GenericMapper<Budget> {
    public Budget createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("budget_id");
        Car car = new CarMapper().createEntity(rs);
        double price = rs.getDouble("budget_price");
        Date date_start = rs.getDate("budget_date_start");
        Date date_finish = rs.getDate("budget_date_finish");
        
        LocalDate start = null, finish = null;
        if (date_start != null)
            start = date_start.toLocalDate();
        if (date_finish != null)
            finish = date_finish.toLocalDate();

        return new Budget(id, null, null, car, price, start, finish);
    }

    public ArrayList<Budget> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<Budget> budgets = new ArrayList<Budget>();

        while (rs.next())
            budgets.add(createEntity(rs));

        return budgets;
    }
}
