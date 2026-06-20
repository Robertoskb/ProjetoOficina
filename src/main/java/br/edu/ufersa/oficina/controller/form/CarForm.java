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
    @FXML private ComboBox<String> brandField;
    @FXML private ComboBox<String> modelField;
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

        brandField.getItems().addAll(
                "Chevrolet", "Fiat", "Volkswagen", "Toyota",
                "Honda", "Hyundai", "Renault", "Nissan", "Jeep", "Ford"
        );

        brandField.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;
            if (newVal.equals("Chevrolet"))
                modelField.getItems().addAll("Onix", "Tracker", "Spin", "Cruze", "S10", "Celta");
            else if (newVal.equals("Fiat"))
                modelField.getItems().addAll("Strada", "Cronos", "Toro", "Argo", "Mobi", "Pulse");
            else if (newVal.equals("Volkswagen"))
                modelField.getItems().addAll("Polo", "Virtus", "Nivus", "Gol", "T-Cross", "Saveiro");
            else if (newVal.equals("Toyota"))
                modelField.getItems().addAll("Corolla", "Yaris", "Hilux", "RAV4", "SW4");
            else if (newVal.equals("Honda"))
                modelField.getItems().addAll("Civic", "Fit", "HR-V", "CR-V", "City");
            else if (newVal.equals("Hyundai"))
                modelField.getItems().addAll("HB20", "Creta", "Tucson", "Santa Fe", "i30");
            else if (newVal.equals("Renault"))
                modelField.getItems().addAll("Kwid", "Sandero", "Logan", "Duster", "Captur");
            else if (newVal.equals("Nissan"))
                modelField.getItems().addAll("Versa", "Kicks", "Frontier", "Sentra");
            else if (newVal.equals("Jeep"))
                modelField.getItems().addAll("Renegade", "Compass", "Commander");
            else if (newVal.equals("Ford"))
                modelField.getItems().addAll("Ka", "EcoSport", "Ranger", "Bronco Sport");
        });

        clientField.getItems().addAll(clientService.getAllClients());
        clientField.setConverter(new StringConverter<>() {
            @Override
            public String toString(Client c) { return c != null ? c.getName() : ""; }
            @Override
            public Client fromString(String s) { return null; }
        });

        yearField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*"))
                yearField.setText(newVal.replaceAll("[^\\d]", ""));
            if (yearField.getText().length() > 4)
                yearField.setText(yearField.getText().substring(0, 4));
        });

        mileageField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*"))
                mileageField.setText(newVal.replaceAll("[^\\d]", ""));
        });

        if (entity != null && entity.isValid())
            fill();
    }

    @Override
    public void fill() {

        clientField.setValue(entity.getClient());
        brandField.setValue(entity.getBrand());
        modelField.setValue(entity.getModel());
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
        entity.setBrand(brandField.getValue());
        entity.setModel(modelField.getValue());
        entity.setColor(colorField.getText());
        entity.setPlate(plateField.getText());
        entity.setYear(Integer.parseInt(yearField.getText()));
        entity.setMileage(Integer.parseInt(mileageField.getText()));

    }
}