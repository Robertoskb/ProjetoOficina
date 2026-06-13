package br.edu.ufersa.oficina.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.ufersa.oficina.model.entity.Parts;
import br.edu.ufersa.oficina.model.entity.Service;
import br.edu.ufersa.oficina.model.entity.Treatment;

public class TreatmentPartServiceDAO{
    private String treatmentParts;
    private String treatmentServices;
    private String base;

    public TreatmentPartServiceDAO(String base, String treatmentParts, String treatmentServices){
        setBase(base);
        setTreatmentParts(treatmentParts);
        setTreatmentServices(treatmentServices);
    }
    
    public void addPart(Treatment treatment, Connection conn) throws SQLException {
        String sql = "INSERT INTO " + treatmentParts + " (" + base + "_id, part_id) " + "VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Parts part: treatment.getParts()){
                ps.setInt(1, treatment.getId());
                ps.setInt(2, part.getId());
                ps.addBatch();
            }
            
            ps.executeBatch();
        }

    }

    public void addService(Treatment treatment, Connection conn) throws SQLException {
        String sql = "INSERT INTO " + treatmentServices + " (" + base + "_id, service_id) " + "VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Service service: treatment.getServices()){
                ps.setInt(1, treatment.getId());
                ps.setInt(2, service.getId());
                ps.addBatch();
            }
            
            ps.executeBatch();
        }

    }

    public void addComplete(Treatment treatment, Connection conn) throws SQLException {
        addPart(treatment, conn);
        addService(treatment, conn);
    }

    public void updatePart(Treatment treatment, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM " + treatmentParts + " WHERE " + base + "_id = ?";

        String insertRelation = "INSERT INTO " + treatmentParts + " (" + base + "_id, part_id) VALUES (?, ?)";

        try (PreparedStatement psDelete = conn.prepareStatement(deleteRelations)) {
            psDelete.setInt(1, treatment.getId());
            psDelete.executeUpdate();
        }

        try (PreparedStatement psInsert = conn.prepareStatement(insertRelation)) {
            for (Parts part : treatment.getParts()) {
                psInsert.setInt(1, treatment.getId());
                psInsert.setInt(2, part.getId());
                psInsert.addBatch();
            }
            
            psInsert.executeBatch();
        }


    }

    public void updateService(Treatment treatment, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM " + treatmentServices + " WHERE " + base +"_id = ?";

        String insertRelation = "INSERT INTO " + treatmentServices + " (" + base + "_id, service_id) VALUES (?, ?)";

        try (PreparedStatement psDelete = conn.prepareStatement(deleteRelations)) {
            psDelete.setInt(1, treatment.getId());
            psDelete.executeUpdate();
        }

        try (PreparedStatement psInsert = conn.prepareStatement(insertRelation)) {
            for (Service service : treatment.getServices()) {
                psInsert.setInt(1, treatment.getId());
                psInsert.setInt(2, service.getId());
                psInsert.addBatch();
            }
            
            psInsert.executeBatch();
        }
    }

    public void updateComplete(Treatment treatment, Connection conn) throws SQLException{
        updateService(treatment, conn);
        updatePart(treatment, conn);
    }

    public String getTreatmentServices() {
        return treatmentServices;
    }

    public void setTreatmentServices(String treatmentServices) {
        this.treatmentServices = treatmentServices;
    }

    public String getTreatmentParts() {
        return treatmentParts;
    }

    public void setTreatmentParts(String treatmentParts) {
        this.treatmentParts = treatmentParts;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}