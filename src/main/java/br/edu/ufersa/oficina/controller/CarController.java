package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardAdd;
import br.edu.ufersa.oficina.components.CardGeneric;
import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.CarForm;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.PaginationList;
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

    private final ClientService clientService = new ClientService();

    public CarController(ScreenManager screenManager) {
        super(screenManager, new CarService());
    }

    @Override
    public void initialize() throws IOException {
        filterClient.getItems().add(null);
        try { filterClient.getItems().addAll(clientService.getAllClients()); } catch (Exception ignored) {}

        filterClient.setConverter(new StringConverter<>() {
            @Override public String toString(Client c) { return c != null ? c.getName() : "Todos"; }
            @Override public Client fromString(String s) { return null; }
        });

        super.initialize();
    }

    @Override
    public void generateCards() throws IOException {
        ArrayList<CardGeneric> baseCards = ((PaginatorController) this).cards;
        for (Car car : service.getAllCars()) {
            CardSubject card = new CardSubject();
            card.setCardId(car.getId());
            card.setTitle(car.getModel());
            card.setDescription(car.getPlate());
            card.registerObserver(this);
            baseCards.add(card);
        }
    }

    @FXML
    public void filter() {
        try {
            Client client = filterClient.getValue();
            String plate = filterPlate.getText() != null ? filterPlate.getText().toLowerCase().trim() : "";

            ArrayList<Car> filtered = new ArrayList<>();
            for (Car car : service.getAllCars()) {
                boolean matchClient = client == null || car.getClient().getId() == client.getId();
                boolean matchPlate = plate.isEmpty() || car.getPlate().toLowerCase().contains(plate);

                if (matchClient && matchPlate) {
                    filtered.add(car);
                }
            }

            ArrayList<CardGeneric> baseCards = ((PaginatorController) this).cards;
            baseCards.clear();
            cardContainer.getChildren().clear();

            CardAdd cardAdd = new CardAdd();
            cardAdd.registerObserver(this);
            baseCards.add(cardAdd);

            for (Car car : filtered) {
                CardSubject card = new CardSubject();
                card.setCardId(car.getId());
                card.setTitle(car.getModel());
                card.setDescription(car.getPlate());
                card.registerObserver(this);
                baseCards.add(card);
            }

            paginationList = new PaginationList<>(baseCards, perPage);
            updatePage(0);
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @FXML
    public void clearFilter() {
        filterClient.setValue(null);
        filterPlate.clear();

        try {
            ArrayList<CardGeneric> baseCards = ((PaginatorController) this).cards;
            baseCards.clear();
            cardContainer.getChildren().clear();

            CardAdd cardAdd = new CardAdd();
            cardAdd.registerObserver(this);
            baseCards.add(cardAdd);

            generateCards();

            paginationList = new PaginationList<>(baseCards, perPage);
            updatePage(0);
        } catch (IOException e) {
            alert(e.getMessage());
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