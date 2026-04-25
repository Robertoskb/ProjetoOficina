package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

class BudgetFactory{
    public static Budget createSimpleBudget(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        Car car = new CarDAO().getCarById(rs.getInt("car_id"));
        double price = rs.getDouble("price");
        LocalDate date_start = rs.getDate("date_start").toLocalDate();
        LocalDate date_finish = rs.getDate("date_finish").toLocalDate();

        return new Budget(id, null, null, car, price, date_start, date_finish);
    }

    public static Budget createBudget(ResultSet rs, ArrayList<Parts> parts, ArrayList<Service> services) throws SQLException{
        int id = rs.getInt("id");
        Car car = new CarDAO().getCarById(rs.getInt("car_id"));
        double price = rs.getDouble("price");
        LocalDate date_start = rs.getDate("date_start").toLocalDate();
        LocalDate date_finish = rs.getDate("date_finish").toLocalDate();

        return new Budget(id, parts, services, car, price, date_start, date_finish);
    }

    public static ArrayList<Budget> createArrayBudget(ResultSet rs) throws SQLException{
        ArrayList<Budget> budgets = new ArrayList<Budget>();

        while (rs.next())
            budgets.add(createSimpleBudget(rs));

        return budgets;
    }
}

public class BudgetDAO extends GenericDAO{
    public BudgetDAO(){
        super("Budget");
    }


    public ArrayList<Budget> getAllBudget(){
        ResultSet rs = getAll();

        if (rs == null) return null;

        try {
            return BudgetFactory.createArrayBudget(rs);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public Budget getBudgetById(int id){
        ResultSet rs = filterById(id);

        if (rs == null) return null;

        try {
            ArrayList<Parts> parts = getPartsByBudget(id);
            ArrayList<Service> services = getServiceByBudget(id);

            return BudgetFactory.createBudget(rs, parts, services);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Budget> getBudgetByCar(Car car){
        ResultSet rs = filter("car_id", Integer.toString(car.getId()));

        if (rs == null) return null;

        try {
            return BudgetFactory.createArrayBudget(rs);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Budget> getBudgetByClient(Client client){
        Connection conn = ConnectionDB.getConnection();

        String sql = " SELECT b.* FROM Budget b JOIN Car c ON b.car_id = c.id  WHERE c.client_id = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, client.getId());

            return BudgetFactory.createArrayBudget(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Budget> getBudgetByPeriod(LocalDate start, LocalDate end){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM Budget WHERE date_start BETWEEN ? AND ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));

            return BudgetFactory.createArrayBudget(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    private ArrayList<Parts> getPartsByBudget(int id){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = "SELECT p.* FROM Parts p JOIN Budget_Parts bp ON p.id = bp.part_id WHERE bp.budget_id = ? ";

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

    private ArrayList<Service> getServiceByBudget(int id){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = "SELECT s.* FROM Parts s JOIN Budget_Service bs ON s.id = bs.part_id WHERE bs.budget_id = ? ";

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
