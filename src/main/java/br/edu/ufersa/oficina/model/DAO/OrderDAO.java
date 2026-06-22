package br.edu.ufersa.oficina.model.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Mappers.OrderMapper;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.Order;


public class OrderDAO extends TransactionDAO<Order> {
    public OrderDAO() {
        super("`Order`", new OrderMapper(), "order");
    }

    public ArrayList<Order> getTransactionCompleteThisMonth(){
        Connection conn = ConnectionDB.getConnection();

        Date today = Date.valueOf(LocalDate.now().plusDays(1));
        Date month = Date.valueOf(LocalDate.now().withDayOfMonth(1));

        String sql =  "SELECT t.*, ca.*, cl.* FROM " + table + " t LEFT JOIN car ca " +
                "ON t.car_id = ca.car_id LEFT JOIN client cl ON ca.client_id = cl.client_id WHERE t.order_date_finish BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setDate(1, month);
            ps.setDate(2, today);
            try (ResultSet rs = ps.executeQuery()){
                return mapper.createArrayEntity(rs);
            }

        }
        catch (SQLException e) {
            throw new MecException(e.getMessage());
        }

    }

    public ArrayList<Order> getTransactionPaidPending() {
        Connection conn = ConnectionDB.getConnection();

        String sql =  "SELECT t.*, ca.*, cl.* FROM " + table + " t LEFT JOIN car ca " +
                "ON t.car_id = ca.car_id LEFT JOIN client cl ON ca.client_id = cl.client_id WHERE  t.order_date_finish IS NOT NULL AND t.completed = false";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            return mapper.createArrayEntity(rs);
        }
        catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<Order> getTransactionInProgress() {
        Connection conn = ConnectionDB.getConnection();

        String sql =  "SELECT t.*, ca.*, cl.* FROM " + table + " t LEFT JOIN car ca " +
                "ON t.car_id = ca.car_id LEFT JOIN client cl ON ca.client_id = cl.client_id WHERE t.order_date_finish IS NULL ";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            return mapper.createArrayEntity(rs);
        }
        catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    @Override
    public void insert(Order order) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + table + " (car_id, order_price, order_date_start, order_date_finish, completed) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            conn.setAutoCommit(false);

            ps.setInt(1, order.getCar().getId());
            ps.setDouble(2, order.getPrice());

            Date start = null, finish = null;
            if (order.getDate_start() != null)
                start = Date.valueOf(order.getDate_start());

            if (order.getDate_finish() != null)
                finish = Date.valueOf(order.getDate_finish());

            ps.setDate(3, start);
            ps.setDate(4, finish);

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
    public void update(Order order) {
        Connection conn = ConnectionDB.getConnection();

        String sql = "Update " + table + " SET car_id = ?, order_price = ?, order_date_start = ?, order_date_finish = ?, completed = ? WHERE order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            ps.setInt(1, order.getCar().getId());
            ps.setDouble(2, order.getPrice());

            Date start = null, finish = null;
            if (order.getDate_start() != null)
                start = Date.valueOf(order.getDate_start());

            if (order.getDate_finish() != null)
                finish = Date.valueOf(order.getDate_finish());

            ps.setDate(3, start);
            ps.setDate(4, finish);
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

