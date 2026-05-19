package br.edu.ufersa.oficina.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface GenericFactory<T> {
    public T createEntity(ResultSet rs) throws SQLException;
    public ArrayList<T> createArrayEntity(ResultSet rs) throws SQLException;
}
