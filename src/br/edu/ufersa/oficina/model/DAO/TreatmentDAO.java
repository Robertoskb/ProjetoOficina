package br.edu.ufersa.oficina.model.DAO;

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

        base = base.substring(0, 1).toUpperCase() + base.substring(1); // base to Base
        setTreatmentParts(base + treatmentParts);
        setTreatmentServices(base + treatmentServices);

        setTsd(new TreatmentPartServiceDAO(base, treatmentParts, treatmentServices));
    }

    public abstract void addTreatment(T treatment);
    public abstract void updateTreatment(T treatment);

    private ArrayList<Parts> getPartsByTreatment(int id){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;


        String sql = "SELECT p.* FROM Parts p JOIN " + treatmentParts + " tp ON p.id = tp.part_id WHERE tp.budget_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            return new PartsFactory().createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    private ArrayList<Service> getServiceByTreatment(int id){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = "SELECT s.* FROM Service s JOIN " + treatmentServices + " ts ON s.id = ts.service_id WHERE ts.budget_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            return new ServiceFactory().createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public T getTreatmentById(int id){

        T treatment = filterEntityById(id);

        if (treatment != null){
            ArrayList<Parts> parts = getPartsByTreatment(id);
            ArrayList<Service> services = getServiceByTreatment(id);


            treatment.setParts(parts);
            treatment.setServices(services);

            return treatment;
        }

        return null;

    }

    public ArrayList<T> getTreatmentByCar(Car car){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = "SELECT * FROM " + table + " WHERE client_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, car.getId());

            return factory.createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<T> getTreatmentByClient(Client client){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = " SELECT t.* FROM " + table + " t JOIN Car c ON t.car_id = c.id  WHERE c.client_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, client.getId());

            return factory.createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<T> getTreatmentByPeriod(LocalDate start, LocalDate end){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = "SELECT * FROM " + table + " WHERE date_start BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));

            return factory.createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
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
