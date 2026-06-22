package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardEntity;
import br.edu.ufersa.oficina.controller.form.CarForm;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;

public class CarController extends PaginatorController<CarService> {

    @FXML private ComboBox<Client> filterClient;
    @FXML private TextField filterPlate;

    protected ArrayList<Car> currentCars;
    private final ClientService clientService = new ClientService();

    public CarController(CarService carService) {
        super(carService);
        currentCars = service.getAllCars();
    }

    @Override
    public void initialize() throws IOException {
        filterClient.getItems().addAll(clientService.getAllClients());

        filterClient.setConverter(new StringConverter<Client>() {
            @Override
            public String toString(Client client) {
                return client == null ? "" : client.getName();
            }

            @Override
            public Client fromString(String string) {
                return null;
            }
        });

        super.initialize();
    }

    @Override
    public void generateCards() throws IOException {
        for (Car entityCar : currentCars) {
            CardEntity card = new CardEntity();
            card.setCardId(entityCar.getId());

            Client client = entityCar.getClient();
            String clientName = client.getName() != null? client.getName(): "<Cliente Removido>";

            card.setTitle(entityCar.getBrand() + " - " + entityCar.getModel() + " de " + clientName);
            card.setDescription("Placa: " + entityCar.getPlate());
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @FXML
    public void filterByClient() {
        Client selectedClient = filterClient.getValue();
        if (selectedClient != null) {
            try {
                currentCars = service.getCarsByClientId(selectedClient.getId());
                loadPagination();
            } catch (Exception e) {
                alert(e.getMessage());
            }
        } else {
            clearFilter();
        }
    }

    @FXML
    public void filterByPlate() {
        String plate = filterPlate.getText();
        if (plate != null && !plate.trim().isEmpty()) {
            try {
                Car foundCar = service.getCarByPlate(plate.trim());
                currentCars = new ArrayList<>();
                currentCars.add(foundCar);
                loadPagination();
            } catch (Exception e) {
                alert(e.getMessage());
            }
        } else {
            clearFilter();
        }
    }

    @FXML
    public void clearFilter() {
        filterPlate.clear();
        filterClient.setValue(null);

        try {
            currentCars = service.getAllCars();
            loadPagination();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/carForm.fxml");
            loader.setController(new CarForm(new Car(), service));
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
            Car c = service.getCarById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/carForm.fxml");
            loader.setController(new CarForm(c, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}