package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Entity.Entity;
import br.edu.ufersa.oficina.model.Mappers.GenericMapper;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class GenericDAO<E extends Entity> {
    protected String table;
    protected GenericMapper<E> mapper;
    protected String prefix;

    public GenericDAO(String table, GenericMapper<E> mapper){
        setTable(table);
        setMapper(mapper);
        setPrefix(table.toLowerCase().replace("`", ""));

    }

    public abstract void insert(E entity);
    public abstract void update(E entity);

    public void delete(int id){
        Connection conn = ConnectionDB.getConnection();

        String sql = "DELETE FROM " + this.table + " WHERE " + prefix + "_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.execute();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<E> getAllEntities(){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + this.table + " ORDER BY 1 DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            return mapper.createArrayEntity(rs);
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public ArrayList<E> filterArrayEntity(String column, String value){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + this.table + " WHERE " + column + " = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                return mapper.createArrayEntity(rs);
            }

        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public E filterEntity(String column, String value){
        Connection conn = ConnectionDB.getConnection();

        String sql = "SELECT * FROM " + this.table + " WHERE " + column + " = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return mapper.createEntity(rs);
                return null;
            }

        } catch (SQLException e) {
            throw new MecException(e.getMessage());
        }
    }

    public E filterEntityById(int id) {
        return filterEntity(prefix + "_id", Integer.toString(id));
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public GenericMapper<E> getMapper() {
        return mapper;
    }

    public void setMapper(GenericMapper<E> mapper) {
        this.mapper = mapper;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
