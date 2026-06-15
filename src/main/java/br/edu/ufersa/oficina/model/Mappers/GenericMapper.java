package br.edu.ufersa.oficina.model.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface GenericMapper<T> {
    public T createEntity(ResultSet rs) throws SQLException;
    public ArrayList<T> createArrayEntity(ResultSet rs) throws SQLException;
}
