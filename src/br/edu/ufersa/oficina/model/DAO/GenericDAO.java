package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Factories.GenericFactory;
import br.edu.ufersa.oficina.model.connection.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenericDAO<E> {
    protected String table;
    protected GenericFactory<E> factory;

    public GenericDAO(String table, GenericFactory<E> factory){
        setTable(table);
        setFactory(factory);
    }

    public void delete(int id){
        Connection conn = ConnectionDB.getConnection();

        String sql = "DELETE FROM " + this.table + " WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<E> getAllEntity(){
        Connection conn = ConnectionDB.getConnection();
        System.out.println(table);

        String sql = "SELECT * FROM " + this.table;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            return factory.createArrayEntity(ps.executeQuery());
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    private ResultSet filter(String column, String value){
        Connection conn = ConnectionDB.getConnection();


        String sql = "SELECT * FROM " + this.table + " WHERE " + column + " = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);

            return ps.executeQuery();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<E> filterArrayEntity(String column, String value){
        try {
            ResultSet rs = filter(column, value);
            return factory.createArrayEntity(rs);

        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public E filterEntity(String column, String value){
        try {
            ResultSet rs = filter(column, value);
            if (rs != null)
                return factory.createEntity(rs);
            return null;
        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public E filterEntityById(int id) {
        return filterEntity("id", Integer.toString(id));
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public GenericFactory<E> getFactory() {
        return factory;
    }

    public void setFactory(GenericFactory<E> factory) {
        this.factory = factory;
    }
}
