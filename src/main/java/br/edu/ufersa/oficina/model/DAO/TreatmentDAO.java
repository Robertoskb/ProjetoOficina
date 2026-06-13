package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Factories.GenericFactory;
import br.edu.ufersa.oficina.model.Factories.PartsFactory;
import br.edu.ufersa.oficina.model.Factories.ServiceFactory;
import br.edu.ufersa.oficina.model.connection.ConnectionDB;
import br.edu.ufersa.oficina.model.entity.Treatment;
import br.edu.ufersa.oficina.model.entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class TreatmentDAO<T extends Treatment> extends GenericDAO<T>{
    protected String treatmentParts = "_Parts";
    protected String treatmentServices = "_Services";
    protected String base;
    protected TreatmentPartServiceDAO tsd;

    public TreatmentDAO(String table, GenericFactory<T> factory, String base){
        super(table, factory);
        setBase(base);

        String base_temp = base.substring(0, 1).toUpperCase() + base.substring(1); // base to Base
        setTreatmentParts(base_temp + treatmentParts);
        setTreatmentServices(base_temp + treatmentServices);

        setTsd(new TreatmentPartServiceDAO(base, treatmentParts, treatmentServices));
    }

    public abstract void addTreatment(T treatment);
    public abstract void updateTreatment(T treatment);

    @Override
    public ArrayList<T> getAllEntity(){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT t.*, ca.*, cl.* FROM " + table + " t INNER JOIN car ca ON t.car_id = ca.car_id INNER JOIN client cl ON ca.client_id = cl.client_id";

        ArrayList<T> treatments;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            treatments = factory.createArrayEntity(rs);

        }

        catch (SQLException e) {
            throw new MecException(e.getMessage());
        }

        return treatments;
    }

    private ArrayList<Parts> getPartsByTreatment(int id){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT p.* FROM Parts p JOIN " + treatmentParts + " tp ON p.part_id = tp.part_id WHERE " + base + "_id = ? ";

        ArrayList<Parts> parts;
        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            parts = new PartsFactory().createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }

        return parts;
    }

    private ArrayList<Service> getServiceByTreatment(int id){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT s.* FROM Service s JOIN " + treatmentServices + " ts ON s.service_id = ts.service_id WHERE " + base + "_id = ?";

        ArrayList<Service> services;
        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            services = new ServiceFactory().createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }

        return services;
    }

    public T getTreatmentById(int id){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT t.*, ca.*, cl.* FROM " + table + " t INNER JOIN car ca ON t.car_id = ca.car_id INNER JOIN client cl ON ca.client_id = cl.client_id where " + base + "_id = ?";

        T treatment;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    treatment = factory.createEntity(rs);
                else
                    treatment = null;
            }
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }

        if (treatment != null){
            ArrayList<Parts> parts = getPartsByTreatment(id);
            ArrayList<Service> services = getServiceByTreatment(id);

            treatment.setParts(parts);
            treatment.setServices(services);
        }

        return treatment;

    }

    public ArrayList<T> getTreatmentByCar(Car car){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + table + " WHERE car_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, car.getId());

            return factory.createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<T> getTreatmentByClient(Client client){
        Connection conn = ConnectionDB.getConnection();

        String sql = " SELECT t.* FROM " + table + " t JOIN Car c ON t.car_id = c.id  WHERE c.client_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, client.getId());

            return factory.createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<T> getTreatmentByPeriod(LocalDate start, LocalDate end){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + table + " WHERE " + base + "_date_start BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));

            return factory.createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }


    public String getTreatmentParts() {
        return treatmentParts;
    }

    public void setTreatmentParts(String treatmentParts) {
        this.treatmentParts = treatmentParts;
    }

    public String getTreatmentServices() {
        return treatmentServices;
    }

    public void setTreatmentServices(String treatmentServices) {
        this.treatmentServices = treatmentServices;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public TreatmentPartServiceDAO getTsd() {
        return tsd;
    }

    public void setTsd(TreatmentPartServiceDAO tsd) {
        this.tsd = tsd;
    }
}
