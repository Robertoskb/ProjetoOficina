package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Mappers.BudgetMapper;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.*;

import java.sql.*;


public class BudgetDAO extends TreatmentDAO<Budget>{
    public BudgetDAO(){
        super("budget", new BudgetMapper(), "budget");
    }

    public void insert(Budget budget){
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + table + " (car_id, budget_price, budget_date_start, budget_date_finish) " +
                            "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            conn.setAutoCommit(false);

            ps.setInt(1, budget.getCar().getId());
            ps.setDouble(2, budget.getPrice());

            Date start = null, finish = null;
            if (budget.getDate_start() != null)
                start = Date.valueOf(budget.getDate_start());

            if (budget.getDate_finish() != null)
                finish = Date.valueOf(budget.getDate_finish());

            ps.setDate(3, start);
            ps.setDate(4, finish);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    budget.setId(rs.getInt(1));
                else
                    throw new SQLException("Falha ao carregar o ID do Orçamento");
            }

            tsd.addComplete(budget, conn);

            conn.commit();
        }

        catch (SQLException e){
            try {
                conn.rollback();
                throw new MecException(e.getMessage());
            }

            catch (SQLException e1){
                throw new MecException(e1.getMessage());
            }
        }

        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }

    public void update(Budget budget){
        Connection conn = ConnectionDB.getConnection();

        String sql = "Update " + table + " SET car_id = ?, budget_price = ?, budget_date_start = ?, budget_date_finish = ? WHERE budget_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            conn.setAutoCommit(false);

            ps.setInt(1, budget.getCar().getId());
            ps.setDouble(2, budget.getPrice());

            Date start = null, finish = null;
            if (budget.getDate_start() != null)
                start = Date.valueOf(budget.getDate_start());

            if (budget.getDate_finish() != null)
                finish = Date.valueOf(budget.getDate_finish());

            ps.setDate(3, start);
            ps.setDate(4, finish);

            ps.setInt(5, budget.getId());

            ps.executeUpdate();

            tsd.updateComplete(budget, conn);

            conn.commit();
        }

        catch (SQLException e){
            try {
                conn.rollback();
                throw new MecException(e.getMessage());
            }

            catch (SQLException e1){
                throw new MecException(e1.getMessage());
            }
        }

        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }
}