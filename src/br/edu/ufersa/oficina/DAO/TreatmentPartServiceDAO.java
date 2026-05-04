package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.entity.Treatment;
import br.edu.ufersa.oficina.entity.Parts;
import br.edu.ufersa.oficina.entity.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TreatmentPartServiceDAO{
    private String base;
    
    public TreatmentPartServiceDAO(String base){
        setBase(base);
    }
    
    public void addPart(Treatment treatment, Connection conn) throws SQLException {
        String sql = "INSERT INTO " + base + "_Parts (order_id, part_id) " + "VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        for (Parts part: treatment.getParts()){
            ps.setInt(1, treatment.getId());
            ps.setInt(2, part.getId());
            ps.addBatch();
        }

        ps.executeBatch();
        ps.close();

    }

    public void addService(Treatment treatment, Connection conn) throws SQLException {
        String sql = "INSERT INTO " + base + "_Services (order_id, service_id) " + "VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        for (Service service: treatment.getServices()){
            ps.setInt(1, treatment.getId());
            ps.setInt(2, service.getId());
            ps.addBatch();
        }

        ps.executeBatch();
        ps.close();

    }

    public void addComplete(Treatment treatment, Connection conn) throws SQLException {
        addPart(treatment, conn);
        addService(treatment, conn);
    }

    public void updatePart(Treatment treatment, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM " + base + "_Parts WHERE order_id = ?";

        String insertRelation = "INSERT INTO " + base + "_Parts (order_id, part_id) VALUES (?, ?)";

        PreparedStatement psDelete = conn.prepareStatement(deleteRelations);
        psDelete.setInt(1, treatment.getId());
        psDelete.executeUpdate();

        psDelete.close();

        PreparedStatement psInsert = conn.prepareStatement(insertRelation);

        for (Parts part : treatment.getParts()) {
            psInsert.setInt(1, treatment.getId());
            psInsert.setInt(2, part.getId());
            psInsert.addBatch();
        }

        psInsert.executeBatch();
        psInsert.close();

    }

    public void updateService(Treatment treatment, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM " + base + "_Services WHERE order_id = ?";

        String insertRelation = "INSERT INTO " + base + "_Parts (order_id, service_id) VALUES (?, ?)";

        PreparedStatement psDelete = conn.prepareStatement(deleteRelations);
        psDelete.setInt(1, treatment.getId());
        psDelete.executeUpdate();
        psDelete.close();

        PreparedStatement psInsert = conn.prepareStatement(insertRelation);

        for (Service service : treatment.getServices()) {
            psInsert.setInt(1, treatment.getId());
            psInsert.setInt(2, service.getId());
            psInsert.addBatch();
        }

        psInsert.executeBatch();
        psInsert.close();
    }

    public  void updateComplete(Treatment treatment, Connection conn) throws SQLException{
        updateService(treatment, conn);
        updatePart(treatment, conn);
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}