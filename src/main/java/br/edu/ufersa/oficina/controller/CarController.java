package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.CarForm;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class CarController extends PaginatorController<CarService> {

    public CarController(ScreenManager screenManager) {
        super(screenManager, new CarService());
    }

    @Override
    public void generateCards() throws IOException {
        for (Car car : service.getAllCars()) {
            CardSubject card = new CardSubject();
            card.setCardId(car.getId());
            card.setTitle(car.getModel());
            card.setDescription(car.getPlate());
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/carForm.fxml");
            loader.setController(new CarForm(screenManager, new Car(), service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void edit(int id) {
        try {
            Car car = service.getCarById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/carForm.fxml");
            loader.setController(new CarForm(screenManager, car, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}