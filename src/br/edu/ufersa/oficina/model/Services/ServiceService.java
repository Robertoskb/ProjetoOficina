package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.ServiceDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.entity.Service;

import java.util.ArrayList;

public class ServiceService {
    private final ServiceDAO dao = new ServiceDAO();

    public void addService(Service service){
        String name, price;
        name = service.getName();
        price = service.getPrice();

        if (!name.trim().isEmpty() && !price.trim().isEmpty()){
            dao.addService(service);
        }
        else {
            throw new MecException("Campos vazios");
        }
    }

    public Service getServiceById(int id){
        Service service = dao.filterEntityById(id);

        if (service == null)
            throw new MecNotFoundException("Serviço não Encontrado");

        return service;
    }

    public ArrayList<Service> getAllServices(){
        ArrayList<Service> services = dao.getAllService();

        if (services == null)
            throw new MecNotFoundException("Nenhum serviço encontrado");

        return services;
    }


    public ServiceDAO getDao() {
        return dao;
    }
}
