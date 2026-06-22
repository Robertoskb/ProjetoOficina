package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.Entity.Entity;

public interface GenericService<E extends Entity>{
    public void insert(E entity);
    public void update(E entity);
    public void delete(int id);
}
