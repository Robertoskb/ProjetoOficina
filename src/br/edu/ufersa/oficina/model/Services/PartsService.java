package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.PartsDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.entity.Parts;

import java.util.ArrayList;

public class PartsService {
    
    private final PartsDAO dao = new PartsDAO();

    public void addPart(Parts part) {
        
        if(part.getName().trim().isEmpty())

            throw new MecException("Nome inválido");

        if(part.getManufacturer().trim().isEmpty())

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

    public Parts getPartById(int id) {
        Parts part = dao.getPartById(id);

        if (part == null) {
            throw new MecNotFoundException("Peça não encontrada");
        }

        return part;
    }

    public void deletePart(int id){

        getPartById(id);
        dao.delete(id);

    }

    public Parts getPartsByName(String name){

        Parts part = dao.getPartByName(name);

        if(part == null){

            throw new MecNotFoundException("Peça com esse nome não encontrada");

        }

        return part;

    }

    public Parts getPartByManufacturer(String manufacturer){

        Parts part = dao.getPartByManufacturer(manufacturer);

        if(part == null){

            throw new MecNotFoundException("Peça desse fabricante não encontrada");

        }

        return part;

    }

    public Parts getPartByModel(String model){

        Parts part = dao.getPartByModel(model);

        if(part == null){

            throw new MecNotFoundException("Peça desse modelo não encontrada");

        }

        return part;

    }

    public ArrayList<Parts> getAllParts(){

        return dao.getAllParts();
        
    }

    public PartsDAO getDao() {

        return dao;
        
    }

}
