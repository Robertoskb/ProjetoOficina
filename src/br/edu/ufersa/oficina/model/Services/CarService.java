package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.CarDAO;
import br.edu.ufersa.oficina.modelExceptions.MecException;
import br.edu.ufersa.oficina.modelExceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.entity.Car;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CarService {
    
    private final CarDAO dao = new CarDAO();
   
    public void addCar(Car car){
       
        if(car.getBrand().trim().isEmpty())
        
            throw new MecException("Marca inválida");
        
        if(car.getModel().trim().isEmpty())
         
            throw new MecException("Modelo inválido");
        
        if(car.getColor().trim().isEmpty())
        
            throw new MecException("Cor inválida");
       
        if(car.getPlate().trim().isEmpty())
        
            throw new MecException("Placa inválida");
       
        if(car.getYear() <= 0)
        
            throw new MecException("Ano inválido");
        
        if(car.getMileage() < 0)
       
            throw new MecException("Quilometragem inválida");
        
        if(car.getClient() == null)
            throw new MecException("Cliente inválido");

        if(dao.getByPlate(car.getPlate()) != null)
        
            throw new MecException("Carro já cadastrado");  

        dao.add(car);
    }

    public void updateCar(Car car){

        if(getCarById(car.getId()) == null)
        
            throw new MecNotFoundException("Carro não encontrado");
       
        dao.update(car);

    }

    public void deleteCar(int id){

        if(getCarById(id) == null)
        
            throw new MecNotFoundException("Carro não encontrado");
        
        dao.delete(id);

    }

    public Car getCarByPlate(String plate){
        
        Car car = dao.getByPlate(plate);

        if(car == null)
        
            throw new MecNotFoundException("Carro não encontrado");
        
        return car;

    }

    public ArrayList<Car> getCarsByClientId(int clientId){
        
        return dao.getCarsByClientId();

    }
    public ArrayList<Car> getAllCars(){

        return dao.getAllCars();

    }

    public CarDAO getDao() {

        return dao;

    }
}
