package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class CarForm extends Form<Car, CarService> {

    @FXML private ComboBox<Client> clientField;
    @FXML private TextField modelField;
    @FXML private TextField brandField;
    @FXML private TextField colorField;
    @FXML private TextField plateField;
    @FXML private TextField yearField;
    @FXML private TextField mileageField;

    private final ClientService clientService = new ClientService();

    public CarForm(ScreenManager screenManager, Car entity, CarService service) {
        super(screenManager, entity, service, "Car.fxml");
    }

    public CarForm(ScreenManager screenManager, CarService service) {
        super(screenManager, service, "Car.fxml");
    }

    @FXML
    public void initialize() {
        clientField.getItems().addAll(clientService.getAllClients());
        clientField.setConverter(new StringConverter<>() {
            @Override
            public String toString(Client c) {
                return c != null ? c.getName() : "";
            }
            @Override
            public Client fromString(String s) { return null; }
        });

        if (entity != null && entity.isValid())
            fill();
    }

    @Override
    public void fill() {
        clientField.setValue(entity.getClient());
        modelField.setText(entity.getModel());
        brandField.setText(entity.getBrand());
        colorField.setText(entity.getColor());
        plateField.setText(entity.getPlate());
        yearField.setText(String.valueOf(entity.getYear()));
        mileageField.setText(String.valueOf(entity.getMileage()));
    }

    @Override
    public void setEntityValues() {
        if (entity == null)
            entity = new Car();

        entity.setClient(clientField.getValue());
        entity.setModel(modelField.getText());
        entity.setBrand(brandField.getText());
        entity.setColor(colorField.getText());
        entity.setPlate(plateField.getText());
        entity.setYear(Integer.parseInt(yearField.getText()));
        entity.setMileage(Integer.parseInt(mileageField.getText()));
    }
}