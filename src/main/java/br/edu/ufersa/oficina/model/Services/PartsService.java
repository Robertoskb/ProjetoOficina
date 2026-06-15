package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.PartDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.Entity.Part;

import java.util.ArrayList;

public class PartsService {
    
    private final PartDAO dao = new PartDAO();

    public void addPart(Part part) {
        
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

    public void updatePart(Part part){

        getPartById(part.getId());
        dao.updatePart(part);

    }

    public Part getPartById(int id) {
        Part part = dao.getPartById(id);

        if (part == null) {
            throw new MecNotFoundException("Peça não encontrada");
        }

        return part;
    }

    public void deletePart(int id){

        getPartById(id);
        dao.delete(id);

    }

    public ArrayList<Part> getPartsByName(String name){

        ArrayList<Part> part = dao.getPartsByName(name);

        if(part == null){

            throw new MecNotFoundException("Peça com esse nome não encontrada");

        }

        return part;

    }

    public ArrayList<Part> getPartsByManufacturer(String manufacturer){

        ArrayList<Part> part = dao.getPartsByManufacturer(manufacturer);

        if(part == null){

            throw new MecNotFoundException("Peça desse fabricante não encontrada");

        }

        return part;

    }

    public ArrayList<Part> getPartsByModel(String model){

        ArrayList<Part> part = dao.getPartsByModel(model);

        if(part == null){

            throw new MecNotFoundException("Peça desse modelo não encontrada");

        }

        return part;

    }

    public ArrayList<Part> getAllParts(){

        return dao.getAllParts();
        
    }

    public PartDAO getDao() {

        return dao;
        
    }

}
