package br.edu.ufersa.oficina.DAO;


import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

class OrderFactory{
    public static Order createSimpleOrder(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Car car = new CarDAO().getCarById(rs.getInt("car_id"));
        double price = rs.getDouble("price");
        LocalDate date_start = rs.getDate("date_start").toLocalDate();
        LocalDate date_finish = rs.getDate("date_finish").toLocalDate();
        boolean completed = rs.getBoolean("completed");

        return new Order(id, null, null, car, price, date_start, date_finish, completed);
    }

    public static Order createOrder(ResultSet rs, ArrayList<Parts> parts, ArrayList<Service> services) throws SQLException{
        int id = rs.getInt("id");
        Car car = new CarDAO().getCarById(rs.getInt("car_id"));
        double price = rs.getDouble("price");
        LocalDate date_start = rs.getDate("date_start").toLocalDate();
        LocalDate date_finish = rs.getDate("date_finish").toLocalDate();
        boolean completed = rs.getBoolean("completed");

        return new Order(id, parts, services, car, price, date_start, date_finish, completed);
    }

    public static ArrayList<Order> createArrayOrder(ResultSet rs) throws SQLException{
        ArrayList<Order> orders = new ArrayList<Order>();

        while (rs.next())
            orders.add(createSimpleOrder(rs));

        return orders;
    }
}
public class OrderDAO extends GenericDAO{
    private final TreatmentPartServiceDAO PSD = new TreatmentPartServiceDAO("Order");

    public OrderDAO(){
        super("`Order`");
    }

    public ArrayList<Order> getAllOrder(){
        ResultSet rs = getAll();

        if (rs == null) return null;

        try {
            return OrderFactory.createArrayOrder(rs);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public Order getOrderById(int id){
        ResultSet rs = filterById(id);

        if (rs == null) return null;

        try {
            if (rs.next()){
                ArrayList<Parts> parts = getPartsByOrder(id);
                ArrayList<Service> services = getServiceByOrder(id);

                return OrderFactory.createOrder(rs, parts, services);
            }
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

        }

        return null;

    }

    public ArrayList<Order> getOrderByCar(Car car){
        ResultSet rs = filter("car_id", Integer.toString(car.getId()));

        if (rs == null) return null;

        try {
            return OrderFactory.createArrayOrder(rs);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Order> getOrderByClient(Client client){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = " SELECT b.* FROM Order b JOIN Car c ON b.car_id = c.id  WHERE c.client_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, client.getId());

            return OrderFactory.createArrayOrder(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Order> getOrderByPeriod(LocalDate start, LocalDate end){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = "SELECT * FROM Order WHERE date_start BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));

            return OrderFactory.createArrayOrder(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    private ArrayList<Parts> getPartsByOrder(int id){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = "SELECT p.* FROM Parts p JOIN Order_Parts op ON p.id = op.part_id WHERE op.order_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            return PartsFactory.createArrayParts(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    private ArrayList<Service> getServiceByOrder(int id){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = "SELECT s.* FROM Service s JOIN Order_Services os ON s.id = os.service_id WHERE os.order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            return ServiceFactory.createArrayServices(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public void addOrder(Order order){
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

            PSD.addComplete(order, conn);

            conn.commit();
        }

        catch (SQLException e){
            try {
                conn.rollback();
                System.out.println(e.getMessage());
            }

            catch (SQLException e1){
                System.out.println(e1.getMessage());
            }
        }

        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }

    public void updateOrder(Order order){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

        String sql = "Update " + table + " SET car_id = ?, price = ?, date_start = ?, date_finish = ?, completed = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            conn.setAutoCommit(false);

            ps.setInt(1, order.getCar().getId());
            ps.setDouble(2, order.getPrice());
            ps.setDate(3, Date.valueOf(order.getDate_start()));
            ps.setDate(4, Date.valueOf(order.getDate_finish()));
            ps.setBoolean(5, order.isCompleted());

            ps.setInt(6, order.getId());

            ps.executeUpdate();

            PSD.updateComplete(order, conn);

            conn.commit();
        }

        catch (SQLException e){
            try {
                conn.rollback();
                System.out.println(e.getMessage());
            }

            catch (SQLException e1){
                System.out.println(e1.getMessage());
            }
        }

        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }

    public TreatmentPartServiceDAO getPSD() {
        return PSD;
    }
}