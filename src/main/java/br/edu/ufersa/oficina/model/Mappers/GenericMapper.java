package br.edu.ufersa.oficina.model.Mappers;

import br.edu.ufersa.oficina.model.Entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface GenericMapper<E extends Entity> {
    public E createEntity(ResultSet rs) throws SQLException;
    public ArrayList<E> createArrayEntity(ResultSet rs) throws SQLException;
}
