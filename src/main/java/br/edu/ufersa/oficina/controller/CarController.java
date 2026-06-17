package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.Card;
import br.edu.ufersa.oficina.components.EntityCard;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class CarController extends PaginatorController<Car, CarService>{
    public CarController(ScreenManager screenManager){
        super(screenManager, new CarService());
    }

    @Override
    public void generateCards() throws IOException {
        for (Car car: service.getAllCars()){
            Card card = new EntityCard();
            card.setService(service);
            card.setEntityId(car.getId());
            card.setTitle(car.getModel());
            card.setDescription(car.getPlate());
            card.registerObserver(this);

            cards.add(card);
        }
    }
}
