package br.edu.ufersa.oficina.controller.Paginator;

import br.edu.ufersa.oficina.components.CardEntity;
import br.edu.ufersa.oficina.controller.form.CarForm;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.ClientService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;

public class CarController extends PaginatorController<Car,CarService> {

    @FXML private ComboBox<Client> filterClient;
    @FXML private TextField filterPlate;

    private ClientService clientService;

    public CarController(CarService carService, ClientService clientService) {
        super(carService);
        setClientService(clientService);
        entities = service.getAllCars();
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
        for (Car entityCar : entities) {
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
                entities = service.getCarsByClientId(selectedClient.getId());
                loadPagination();
            } catch (Exception e) {
                error(e.getMessage());
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
                entities = new ArrayList<>();
                entities.add(foundCar);
                loadPagination();
            } catch (Exception e) {
                error(e.getMessage());
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
            entities = service.getAllCars();
            loadPagination();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.loader("form/carForm.fxml");
            loader.setController(new CarForm(new Car(), service, clientService));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    @Override
    public void edit(int id) {
        try {
            Car c = service.getCarById(id);
            FXMLLoader loader = screenManager.loader("form/carForm.fxml");
            loader.setController(new CarForm(c, service, clientService));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}