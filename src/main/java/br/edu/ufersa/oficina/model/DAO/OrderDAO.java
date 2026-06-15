package br.edu.ufersa.oficina.model.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Mappers.OrderMapper;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.Order;


public class OrderDAO extends TreatmentDAO<Order> {
    public OrderDAO() {
        super("`Order`", new OrderMapper(), "order");
    }

    @Override
    public void addTreatment(Order order) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + table + " (car_id, order_price, order_date_start, order_date_finish, completed) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            conn.setAutoCommit(false);

            ps.setInt(1, order.getCar().getId());
            ps.setDouble(2, order.getPrice());
            ps.setDate(3, Date.valueOf(order.getDate_start()));
            ps.setDate(4, Date.valueOf(order.getDate_finish()));
            ps.setBoolean(5, order.isCompleted());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    order.setId(rs.getInt(1));
                else
                    throw new SQLException("Falha ao carregar o ID do Orçamento");
            }

            tsd.addComplete(order, conn);

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new MecException(e.getMessage());
            } catch (SQLException e1) {
                throw new MecException(e1.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public void updateTreatment(Order order) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "Update " + table + " SET car_id = ?, order_price = ?, order_date_start = ?, order_date_finish = ?, completed = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            ps.setInt(1, order.getCar().getId());
            ps.setDouble(2, order.getPrice());
            ps.setDate(3, Date.valueOf(order.getDate_start()));
            ps.setDate(4, Date.valueOf(order.getDate_finish()));
            ps.setBoolean(5, order.isCompleted());

            ps.setInt(6, order.getId());

            ps.executeUpdate();

            tsd.updateComplete(order, conn);

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new MecException(e.getMessage());
            } catch (SQLException e1) {
                throw new MecException(e1.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }
}

