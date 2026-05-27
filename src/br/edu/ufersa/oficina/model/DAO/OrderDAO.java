package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.model.Factories.OrderFactory;
import br.edu.ufersa.oficina.model.connection.ConnectionDB;
import br.edu.ufersa.oficina.model.entity.*;

import java.sql.*;


public class OrderDAO extends TreatmentDAO<Order> {
    public OrderDAO() {
        super("`Order`", new OrderFactory(), "order");
    }

    public void addTreatment(Order order) {
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

        String sql = "INSERT INTO " + table + " (car_id, price, date_start, date_finish, completed) " +
                "VALUES (?, ?, ?, ?)";

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
                System.out.println(e.getMessage());
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    public void updateTreatment(Order order) {
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

        String sql = "Update " + table + " SET car_id = ?, price = ?, date_start = ?, date_finish = ?, completed = ? WHERE id = ?";

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
                System.out.println(e.getMessage());
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }
}

