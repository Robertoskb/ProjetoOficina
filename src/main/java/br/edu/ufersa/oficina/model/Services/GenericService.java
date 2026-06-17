package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.GenericDAO;

public interface GenericService<E>{
    public abstract void insert(E entity);
    public abstract void update(E entity);
    public abstract void delete(int id);
}
