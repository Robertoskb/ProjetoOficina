package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.model.entity.Budget;
import br.edu.ufersa.oficina.model.entity.Parts;
import br.edu.ufersa.oficina.model.entity.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BudgetPartServiceDAO{
    public static void addPart(Budget budget, Connection conn) throws SQLException {
        String sql = "INSERT INTO Budget_Parts (budget_id, part_id) " + "VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        for (Parts part: budget.getParts()){
            ps.setInt(1, budget.getId());
            ps.setInt(2, part.getId());
            ps.addBatch();
        }

        ps.executeBatch();
        ps.close();

    }

    public static void addService(Budget budget, Connection conn) throws SQLException {
        String sql = "INSERT INTO Budget_Services (budget_id, service_id) " + "VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        for (Service service: budget.getServices()){
            ps.setInt(1, budget.getId());
            ps.setInt(2, service.getId());
            ps.addBatch();
        }

        ps.executeBatch();
        ps.close();

    }

    public static void addComplete(Budget budget, Connection conn) throws SQLException {
        addPart(budget, conn);
        addService(budget, conn);
    }

    public static void updatePart(Budget budget, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM Budget_Parts WHERE budget_id = ?";

        String insertRelation = "INSERT INTO Budget_Parts (budget_id, part_id) VALUES (?, ?)";

        PreparedStatement psDelete = conn.prepareStatement(deleteRelations);
        psDelete.setInt(1, budget.getId());
        psDelete.executeUpdate();

        psDelete.close();

        PreparedStatement psInsert = conn.prepareStatement(insertRelation);

        for (Parts part : budget.getParts()) {
            psInsert.setInt(1, budget.getId());
            psInsert.setInt(2, part.getId());
            psInsert.addBatch();
        }

        psInsert.executeBatch();
        psInsert.close();


    }

    public static void updateService(Budget budget, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM Budget_Services WHERE budget_id = ?";

        String insertRelation = "INSERT INTO Budget_Parts (budget_id, service_id) VALUES (?, ?)";

        PreparedStatement psDelete = conn.prepareStatement(deleteRelations);
        psDelete.setInt(1, budget.getId());
        psDelete.executeUpdate();
        psDelete.close();

        PreparedStatement psInsert = conn.prepareStatement(insertRelation);

        for (Service service : budget.getServices()) {
            psInsert.setInt(1, budget.getId());
            psInsert.setInt(2, service.getId());
            psInsert.addBatch();
        }

        psInsert.executeBatch();
        psInsert.close();
    }

    public static void updateComplete(Budget budget, Connection conn) throws SQLException{
        updateService(budget, conn);
        updatePart(budget, conn);
    }
}