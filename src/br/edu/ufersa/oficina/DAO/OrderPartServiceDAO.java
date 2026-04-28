package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.entity.Order;
import br.edu.ufersa.oficina.entity.Parts;
import br.edu.ufersa.oficina.entity.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderPartServiceDAO{
    public static void addPart(Order order, Connection conn) throws SQLException {
        String sql = "INSERT INTO Order_Parts (order_id, part_id) " + "VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        for (Parts part: order.getParts()){
            ps.setInt(1, order.getId());
            ps.setInt(2, part.getId());
            ps.addBatch();
        }

        ps.executeBatch();
        ps.close();

    }

    public static void addService(Order order, Connection conn) throws SQLException {
        String sql = "INSERT INTO Order_Services (order_id, service_id) " + "VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        for (Service service: order.getServices()){
            ps.setInt(1, order.getId());
            ps.setInt(2, service.getId());
            ps.addBatch();
        }

        ps.executeBatch();
        ps.close();

    }

    public static void addComplete(Order order, Connection conn) throws SQLException {
        addPart(order, conn);
        addService(order, conn);
    }

    public static void updatePart(Order order, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM Order_Parts WHERE order_id = ?";

        String insertRelation = "INSERT INTO Order_Parts (order_id, part_id) VALUES (?, ?)";

        PreparedStatement psDelete = conn.prepareStatement(deleteRelations);
        psDelete.setInt(1, order.getId());
        psDelete.executeUpdate();

        psDelete.close();

        PreparedStatement psInsert = conn.prepareStatement(insertRelation);

        for (Parts part : order.getParts()) {
            psInsert.setInt(1, order.getId());
            psInsert.setInt(2, part.getId());
            psInsert.addBatch();
        }

        psInsert.executeBatch();
        psInsert.close();


    }

    public static void updateService(Order order, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM Order_Services WHERE order_id = ?";

        String insertRelation = "INSERT INTO Order_Parts (order_id, service_id) VALUES (?, ?)";

        PreparedStatement psDelete = conn.prepareStatement(deleteRelations);
        psDelete.setInt(1, order.getId());
        psDelete.executeUpdate();
        psDelete.close();

        PreparedStatement psInsert = conn.prepareStatement(insertRelation);

        for (Service service : order.getServices()) {
            psInsert.setInt(1, order.getId());
            psInsert.setInt(2, service.getId());
            psInsert.addBatch();
        }

        psInsert.executeBatch();
        psInsert.close();
    }

    public static void updateComplete(Order order, Connection conn) throws SQLException{
        updateService(order, conn);
        updatePart(order, conn);
    }
}