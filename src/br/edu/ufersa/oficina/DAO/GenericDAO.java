package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.Factories.GenericFactory;
import br.edu.ufersa.oficina.connection.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericDAO<T> {
    protected String table;
    protected GenericFactory<T> factory;

    public GenericDAO(String table, GenericFactory<T> factory){
        setTable(table);
        setFactory(factory);
    }

    public GenericDAO(String table){
        setTable(table);
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
            System.out.println(e.getMessage());

        }
    }

    public ResultSet getALl(){
        Connection conn = ConnectionDB.getConnection();
        System.out.println(table);
        if (conn == null) return null;

        String sql = "SELECT * FROM " + this.table;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();

        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ResultSet getAll(){
        Connection conn = ConnectionDB.getConnection();
        System.out.println(table);
        if (conn == null) return null;

        String sql = "SELECT * FROM " + this.table;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();

        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ResultSet filter(String column, String value){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return null;

        String sql = "SELECT * FROM " + this.table + " WHERE " + column + " = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);

            return ps.executeQuery();

        }

        catch (SQLException e){
            System.out.println(e.getMessage());

            return null;
        }
    }


    public ResultSet filterById(int id) {
        return filter("id", Integer.toString(id));
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public GenericFactory<T> getFactory() {
        return factory;
    }

    public void setFactory(GenericFactory<T> factory) {
        this.factory = factory;
    }
}
