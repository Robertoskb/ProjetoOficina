package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.CarDAO;
import br.edu.ufersa.oficina.modelExceptions.MecException;
import br.edu.ufersa.oficina.modelExceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.entity.Car;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PartsService {
    
    private final PartsDAO dao = new PartsDAO();

    public void addPart(Parts part) {
        
        if(part.getName().trim().isEmpty())

            throw new MecException("Nome inválido");

        if(part.Manufacturer.trim().isEmpty())

            throw new MecException("Fabricante inválido");

        if(part.getModel().trim().isEmpty())

            throw new MecException("Modelo inválido");

        if(part.getPrice() <= 0)

            throw new MecException("Preço inválido");


        dao.addPart(part);

    }

    public void updatePart(Parts part){

        getPartById(part.getId());
        dao.updatePart(part);

    }

    public void deletePart(int id){

        getPartById(id);
        dao.deletePart(id);

    }

    public ArrayList<Parts> getAllParts(){

        return dao.getAllParts();
        
    }

    public PartsDAO getDao() {

        return dao;
        
    }

}
