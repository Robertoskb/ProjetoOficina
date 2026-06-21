package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.ServiceDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.Entity.Service;

import java.util.ArrayList;

public class ServiceService implements GenericService<Service>{
    private final ServiceDAO dao = new ServiceDAO();

    public void insert(Service service){
        String name;
        double price;
        name = service.getName();
        price = service.getPrice();

        if (!name.trim().isEmpty() && (price > 0)){
            dao.insert(service);
        }
        else {
            throw new MecException("Campos vazios");
        }
    }

    public void update(Service service){
        getServiceById(service.getId());

        String name;
        double price;
        name = service.getName();
        price = service.getPrice();

        if (!name.trim().isEmpty() && (price > 0)){
            dao.update(service);
        }
        else {
            throw new MecException("Campos vazios");
        }
    }

    public void delete(int id) {
        getServiceById(id);

        dao.delete(id);
    }

    public Service getServiceById(int id){
        Service service = dao.filterEntityById(id);

        if (service == null)
            throw new MecNotFoundException("Serviço não Encontrado");

        return service;
    }

    public ArrayList<Service> getServiceByName(String name)  {
        ArrayList<Service> services = dao.getServiceByName(name);
        return services;
    }

    public ArrayList<Service> getAllServices(){
        ArrayList<Service> services = dao.getAllServices();
        return services;
    }


    public ServiceDAO getDao() {
        return dao;
    }
}
