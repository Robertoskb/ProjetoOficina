package br.edu.ufersa.oficina.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Entity.Service;
import br.edu.ufersa.oficina.model.Entity.Transaction;

public class TransactionPartServiceDAO{
    private String transactionParts;
    private String transactionServices;
    private String base;

    public TransactionPartServiceDAO(String base, String transactionParts, String transactionServices){
        setBase(base);
        setTransactionParts(transactionParts);
        setTransactionServices(transactionServices);
    }
    
    public void addPart(Transaction transaction, Connection conn) throws SQLException {
        String sql = "INSERT INTO " + transactionParts + " (" + base + "_id, part_id) " + "VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Part part: transaction.getParts()){
                ps.setInt(1, transaction.getId());
                ps.setInt(2, part.getId());
                ps.addBatch();
            }
            
            ps.executeBatch();
        }

    }

    public void addService(Transaction transaction, Connection conn) throws SQLException {
        String sql = "INSERT INTO " + transactionServices + " (" + base + "_id, service_id) " + "VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Service service: transaction.getServices()){
                ps.setInt(1, transaction.getId());
                ps.setInt(2, service.getId());
                ps.addBatch();
            }
            
            ps.executeBatch();
        }

    }

    public void addComplete(Transaction transaction, Connection conn) throws SQLException {
        addPart(transaction, conn);
        addService(transaction, conn);
    }

    public void updatePart(Transaction transaction, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM " + transactionParts + " WHERE " + base + "_id = ?";

        String insertRelation = "INSERT INTO " + transactionParts + " (" + base + "_id, part_id) VALUES (?, ?)";

        try (PreparedStatement psDelete = conn.prepareStatement(deleteRelations)) {
            psDelete.setInt(1, transaction.getId());
            psDelete.executeUpdate();
        }

        try (PreparedStatement psInsert = conn.prepareStatement(insertRelation)) {
            for (Part part : transaction.getParts()) {
                psInsert.setInt(1, transaction.getId());
                psInsert.setInt(2, part.getId());
                psInsert.addBatch();
            }
            
            psInsert.executeBatch();
        }


    }

    public void updateService(Transaction transaction, Connection conn) throws SQLException{
        String deleteRelations = "DELETE FROM " + transactionServices + " WHERE " + base +"_id = ?";

        String insertRelation = "INSERT INTO " + transactionServices + " (" + base + "_id, service_id) VALUES (?, ?)";

        try (PreparedStatement psDelete = conn.prepareStatement(deleteRelations)) {
            psDelete.setInt(1, transaction.getId());
            psDelete.executeUpdate();
        }

        try (PreparedStatement psInsert = conn.prepareStatement(insertRelation)) {
            for (Service service : transaction.getServices()) {
                psInsert.setInt(1, transaction.getId());
                psInsert.setInt(2, service.getId());
                psInsert.addBatch();
            }
            
            psInsert.executeBatch();
        }
    }

    public void updateComplete(Transaction transaction, Connection conn) throws SQLException{
        updateService(transaction, conn);
        updatePart(transaction, conn);
    }

    public String getTransactionServices() {
        return transactionServices;
    }

    public void setTransactionServices(String transactionServices) {
        this.transactionServices = transactionServices;
    }

    public String getTransactionParts() {
        return transactionParts;
    }

    public void setTransactionParts(String transactionParts) {
        this.transactionParts = transactionParts;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}