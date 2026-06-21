package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Mappers.GenericMapper;
import br.edu.ufersa.oficina.model.Mappers.PartsMapper;
import br.edu.ufersa.oficina.model.Mappers.ServiceMapper;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.Transaction;
import br.edu.ufersa.oficina.model.Entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class TransactionDAO<T extends Transaction> extends GenericDAO<T>{
    protected String transactionParts = "_Parts";
    protected String transactionServices = "_Services";
    protected String base;
    protected TransactionPartServiceDAO tsd;

    public TransactionDAO(String table, GenericMapper<T> factory, String base){
        super(table, factory);
        setBase(base);

        String base_temp = base.substring(0, 1).toUpperCase() + base.substring(1); // base to Base
        setTransactionParts(base_temp + transactionParts);
        setTransactionServices(base_temp + transactionServices);

        setTsd(new TransactionPartServiceDAO(base, transactionParts, transactionServices));
    }

    public abstract void insert(T transaction);
    public abstract void update(T transaction);

    @Override
    public ArrayList<T> getAllEntities(){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT t.*, ca.*, cl.* FROM " + table + " t LEFT JOIN car ca ON t.car_id = ca.car_id LEFT JOIN client cl ON ca.client_id = cl.client_id ORDER BY 1 DESC";

        ArrayList<T> transactions;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            transactions = mapper.createArrayEntity(rs);
        }

        catch (SQLException e) {
            throw new MecException(e.getMessage());
        }

        return transactions;
    }

    public ArrayList<Part> getPartsByTransaction(int id){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT p.* FROM Part p JOIN " + transactionParts + " tp ON p.part_id = tp.part_id WHERE " + base + "_id = ? ";

        ArrayList<Part> parts;
        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                parts = new PartsMapper().createArrayEntity(rs);
            }
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }

        return parts;
    }

    public ArrayList<Service> getServicesByTransaction(int id){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT s.* FROM Service s JOIN " + transactionServices + " ts ON s.service_id = ts.service_id WHERE " + base + "_id = ?";

        ArrayList<Service> services;
        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                services = new ServiceMapper().createArrayEntity(rs);
            }
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }

        return services;
    }

    public T getTransactionById(int id){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT t.*, ca.*, cl.* FROM " + table + " t LEFT JOIN car ca ON t.car_id = ca.car_id LEFT JOIN client cl ON ca.client_id = cl.client_id where " + base + "_id = ?";

        T transaction;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    transaction = mapper.createEntity(rs);
                else
                    transaction = null;
            }
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }

        if (transaction != null){
            ArrayList<Part> parts = getPartsByTransaction(id);
            ArrayList<Service> services = getServicesByTransaction(id);

            transaction.setParts(parts);
            transaction.setServices(services);
        }

        return transaction;

    }

    public ArrayList<T> getTransactionsByCar(Car car){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT t.*, ca.*, cl.* FROM " + table + " t LEFT JOIN car ca ON t.car_id = ca.car_id LEFT JOIN client cl ON ca.client_id = cl.client_id WHERE t.car_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, car.getId());

            try (ResultSet rs = ps.executeQuery()){
                return mapper.createArrayEntity(rs);
            }
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<T> getTransactionsByClient(Client client){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT t.*, ca.*, cl.* FROM " + table + " t LEFT JOIN car ca ON t.car_id = ca.car_id LEFT JOIN client cl ON ca.client_id = cl.client_id WHERE cl.client_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, client.getId());

            try (ResultSet rs = ps.executeQuery()){
                return mapper.createArrayEntity(rs);
            }
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<T> getTransactionsByPeriod(LocalDate start, LocalDate end){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT t.*, ca.*, cl.* FROM " + table + " t LEFT JOIN car ca ON t.car_id = ca.car_id LEFT JOIN client cl ON ca.client_id = cl.client_id WHERE " + base + "_date_start BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));

            try (ResultSet rs = ps.executeQuery()){
                return mapper.createArrayEntity(rs);
            }
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }


    public String getTransactionParts() {
        return transactionParts;
    }

    public void setTransactionParts(String transactionParts) {
        this.transactionParts = transactionParts;
    }

    public String getTransactionServices() {
        return transactionServices;
    }

    public void setTransactionServices(String transactionServices) {
        this.transactionServices = transactionServices;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public TransactionPartServiceDAO getTsd() {
        return tsd;
    }

    public void setTsd(TransactionPartServiceDAO tsd) {
        this.tsd = tsd;
    }
}
