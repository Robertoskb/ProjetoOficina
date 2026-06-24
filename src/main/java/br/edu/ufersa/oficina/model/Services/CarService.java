package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.CarDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.Entity.Car;

import java.util.ArrayList;

public class CarService implements GenericService<Car>{

    private final CarDAO dao = new CarDAO();

    public void insert(Car car){

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

        if(dao.getCarByPlate(car.getPlate()) != null)

            throw new MecException("Carro já cadastrado");

        dao.insert(car);
    }

    public void update(Car car){

        getCarById(car.getId());

        dao.update(car);

    }

    public Car getCarById(int id) {

        Car car = dao.getCarById(id);

        if (car == null) {

            throw new MecNotFoundException("Carro não encontrado");

        }

        return car;

    }

    public void delete(int id){

        getCarById(id);

        dao.delete(id);

    }

    public Car getCarByPlate(String plate){

        Car car = dao.getCarByPlate(plate);

        if(car == null)

            throw new MecNotFoundException("Carro não encontrado");

        return car;

    }

    public ArrayList<Car> getCarsByClientId(int clientId){

        return dao.getCarsByClientId(clientId);

    }
    public ArrayList<Car> getAllCars(){

        return dao.getAllCars();

    }

    public CarDAO getDao() {

        return dao;

    }
}
