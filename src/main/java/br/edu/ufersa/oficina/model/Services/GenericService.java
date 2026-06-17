package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.GenericDAO;

public interface GenericService<E>{
    public void insert(E entity);
    public void update(E entity);
    public void delete(int id);
}
