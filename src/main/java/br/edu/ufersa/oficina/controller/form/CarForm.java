package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class CarForm extends Form<Car, CarService> {
    public CarForm(ScreenManager screenManager, Car entity, CarService service) {
        super(screenManager, entity, service);
    }

    @Override
    public void fill() {

    }

    @Override
    public void setEntityValues() {

    }

    @Override
    public void save() throws IOException {

    }
}
