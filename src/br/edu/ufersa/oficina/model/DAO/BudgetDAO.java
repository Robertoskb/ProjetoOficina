package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Factories.BudgetFactory;
import br.edu.ufersa.oficina.model.connection.ConnectionDB;
import br.edu.ufersa.oficina.model.entity.*;

import java.sql.*;


public class BudgetDAO extends TreatmentDAO<Budget>{
    public BudgetDAO(){
        super("Budget", new BudgetFactory(), "budget");
    }

    public void addTreatment(Budget budget){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

        String sql = "INSERT INTO " + table + " (car_id, price, date_start, date_finish) " +
                            "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            conn.setAutoCommit(false);

            ps.setInt(1, budget.getCar().getId());
            ps.setDouble(2, budget.getPrice());
            ps.setDate(3, Date.valueOf(budget.getDate_start()));
            ps.setDate(4, Date.valueOf(budget.getDate_finish()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    budget.setId(rs.getInt(1));
                else
                    throw new SQLException("Falha ao carregar o ID do Orçamento");
            }

            BudgetPartServiceDAO.addComplete(budget, conn);

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

    public void updateTreatment(Budget budget){
        Connection conn = ConnectionDB.getConnection();

        String sql = "Update " + table + " SET car_id = ?, price = ?, date_start = ?, date_finish = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            conn.setAutoCommit(false);

            ps.setInt(1, budget.getCar().getId());
            ps.setDouble(2, budget.getPrice());
            ps.setDate(3, Date.valueOf(budget.getDate_start()));
            ps.setDate(4, Date.valueOf(budget.getDate_finish()));

            ps.setInt(5, budget.getId());

            ps.executeUpdate();

            BudgetPartServiceDAO.updateComplete(budget, conn);

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