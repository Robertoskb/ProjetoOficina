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
    public OrderDAO(){
        super("Order");
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
            ArrayList<Parts> parts = getPartsByOrder(id);
            ArrayList<Service> services = getServiceByOrder(id);

            return OrderFactory.createOrder(rs, parts, services);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
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

        String sql = " SELECT o.* FROM Order o JOIN Car c ON o.car_id = c.id  WHERE c.client_id = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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

        String sql = "SELECT * FROM Order WHERE date_start BETWEEN ? AND ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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

        String sql = "SELECT s.* FROM Parts s JOIN Order_Service os ON s.id = os.part_id WHERE os.order_id = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            return ServiceFactory.createArrayServices(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }
}