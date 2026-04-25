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
            if (rs.next()){
                ArrayList<Parts> parts = getPartsByBudget(id);
                ArrayList<Service> services = getServiceByBudget(id);

                return BudgetFactory.createBudget(rs, parts, services);
            }
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

        }

        return null;

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

        if (conn == null) return null;

        String sql = " SELECT b.* FROM Budget b JOIN Car c ON b.car_id = c.id  WHERE c.client_id = ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
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

        if (conn == null) return null;

        String sql = "SELECT * FROM Budget WHERE date_start BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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

        try (PreparedStatement ps = conn.prepareStatement(sql)){

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

        String sql = "SELECT s.* FROM Service s JOIN Budget_Service bs ON s.id = bs.service_id WHERE bs.budget_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            return ServiceFactory.createArrayServices(ps.executeQuery());
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public void addBudget(Budget budget){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

        String sql = "INSERT INTO " + table + " (car_id, price, date_start, date_finish) " +
                            "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            conn.setAutoCommit(false);

            ps.setInt(1, budget.getCar().getId());
            ps.setDouble(2, budget.getPrice());
            ps.setDate(3, Date.valueOf(budget.getDate_start()));
            ps.setDate(4, Date.valueOf(budget.getDate_finish()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    budget.setId(rs.getInt(1));
                else
                    throw new SQLException("Falha ao carregar o ID do Orçamento");
            }

            BudgetPartServiceDAO.addComplete(budget, conn);

            conn.commit();
        }

        catch (SQLException e){
            try {
                conn.rollback();
                System.out.println(e.getMessage());
            }

            catch (SQLException e1){
                System.out.println(e1.getMessage());
            }
        }

        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }

    public void updateBudget(Budget budget){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

        String sql = "Update " + table + " SET car_id = ?, price = ?, date_start = ?, date_finish = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            conn.setAutoCommit(false);

            ps.setInt(1, budget.getCar().getId());
            ps.setDouble(2, budget.getPrice());
            ps.setDate(3, Date.valueOf(budget.getDate_start()));
            ps.setDate(4, Date.valueOf(budget.getDate_finish()));

            ps.setInt(5, budget.getId());

            ps.executeUpdate();

            BudgetPartServiceDAO.updateComplete(budget, conn);

            conn.commit();
        }

        catch (SQLException e){
            try {
                conn.rollback();
                System.out.println(e.getMessage());
            }

            catch (SQLException e1){
                System.out.println(e1.getMessage());
            }
        }

        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }
}