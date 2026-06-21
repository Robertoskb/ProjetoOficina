package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardAdd;
import br.edu.ufersa.oficina.components.CardGeneric;
import br.edu.ufersa.oficina.components.CardTreatment;
import br.edu.ufersa.oficina.controller.form.OrderForm;
import br.edu.ufersa.oficina.model.Entity.Order;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.OrderService;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderController extends TreatmentController<Order, OrderService> {

    @FXML private ComboBox<Client> filterClient;
    @FXML private ComboBox<Car> filterCar;
    @FXML private TextField filterDateStart;
    @FXML private TextField filterDateEnd;

    private final ClientService clientService = new ClientService();
    private final CarService carService = new CarService();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public OrderController(ScreenManager screenManager) {
        super(screenManager, new OrderService());
    }

    @Override
    public void initialize() throws IOException {
        filterClient.getItems().add(null);
        try { filterClient.getItems().addAll(clientService.getAllClients()); } catch (Exception ignored) {}

        filterClient.setConverter(new StringConverter<>() {
            @Override public String toString(Client c) { return c != null ? c.getName() : "Todos"; }
            @Override public Client fromString(String s) { return null; }
        });

        filterClient.valueProperty().addListener((obs, oldVal, newVal) -> {
            try {
                filterCar.setValue(null);
                filterCar.getItems().clear();
                filterCar.getItems().add(null);
                ArrayList<Car> allCars = carService.getAllCars();
                if (newVal != null) {
                    for (Car c : allCars) {
                        if (c.getClient().getId() == newVal.getId()) filterCar.getItems().add(c);
                    }
                } else {
                    filterCar.getItems().addAll(allCars);
                }
            } catch (Exception ignored) {}
        });

        filterCar.getItems().add(null);
        try { filterCar.getItems().addAll(carService.getAllCars()); } catch (Exception ignored) {}

        filterCar.setConverter(new StringConverter<>() {
            @Override public String toString(Car c) { return c != null ? c.getModel() + " - " + c.getPlate() : "Todos"; }
            @Override public Car fromString(String s) { return null; }
        });

        super.initialize();
    }

    @Override
    public void generateCards() throws IOException {
        ArrayList<CardGeneric> baseCards = ((PaginatorController) this).cards;

        for (Order order : service.getAllTreatments()) {
            CardTreatment card = new CardTreatment();
            if (order.isFinish()) card.removeButton(card.getBtnCheck());
            card.setCardId(order.getId());
            card.setTitle(order.getCar().getModel() + " de " + order.getCar().getClient().getName());
            card.setDescription(String.format("R$ %.2f", order.getPrice()));
            card.registerObserver(this);

            baseCards.add(card);
        }
    }

    @FXML
    public void filterByClient() {
        try {
            filterCar.setValue(null);
            filterDateStart.clear();
            filterDateEnd.clear();

            Client client = filterClient.getValue();
            if (client == null) { clearFilter(); return; }

            ArrayList<Order> filtered = new ArrayList<>();
            for (Order order : service.getAllTreatments()) {
                if (order.getCar().getClient().getId() == client.getId()) {
                    filtered.add(order);
                }
            }
            applyFilteredCards(filtered);
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @FXML
    public void filterByCar() {
        try {
            filterClient.setValue(null);
            filterDateStart.clear();
            filterDateEnd.clear();

            Car car = filterCar.getValue();
            if (car == null) { clearFilter(); return; }

            ArrayList<Order> filtered = new ArrayList<>();
            for (Order order : service.getAllTreatments()) {
                if (order.getCar().getId() == car.getId()) {
                    filtered.add(order);
                }
            }
            applyFilteredCards(filtered);
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @FXML
    public void filterByDate() {
        try {
            filterClient.setValue(null);
            filterCar.setValue(null);

            String dataInicioStr = filterDateStart.getText() != null ? filterDateStart.getText().trim() : "";
            String dataFimStr = filterDateEnd.getText() != null ? filterDateEnd.getText().trim() : "";

            if (dataInicioStr.isEmpty() && dataFimStr.isEmpty()) { clearFilter(); return; }

            LocalDate dataInicio = !dataInicioStr.isEmpty() ? LocalDate.parse(dataInicioStr, fmt) : null;
            LocalDate dataFim = !dataFimStr.isEmpty() ? LocalDate.parse(dataFimStr, fmt) : null;

            ArrayList<Order> filtered = new ArrayList<>();
            for (Order order : service.getAllTreatments()) {
                LocalDate orderDate = order.getDate_start();
                boolean matchDate = true;

                if (orderDate == null) {
                    matchDate = false;
                } else {
                    if (dataInicio != null && orderDate.isBefore(dataInicio)) matchDate = false;
                    if (dataFim != null && orderDate.isAfter(dataFim)) matchDate = false;
                }

                if (matchDate) filtered.add(order);
            }
            applyFilteredCards(filtered);
        } catch (Exception e) {
            alert("Verifique se as datas estão no formato dd/MM/yyyy. Erro: " + e.getMessage());
        }
    }

    private void applyFilteredCards(ArrayList<Order> filteredList) throws IOException {
        ArrayList<CardGeneric> baseCards = ((PaginatorController) this).cards;
        baseCards.clear();
        cardContainer.getChildren().clear();

        CardAdd cardAdd = new CardAdd();
        cardAdd.registerObserver(this);
        baseCards.add(cardAdd);

        for (Order order : filteredList) {
            CardTreatment card = new CardTreatment();
            if (order.isFinish()) card.removeButton(card.getBtnCheck());
            card.setCardId(order.getId());
            card.setTitle(order.getCar().getModel() + " de " + order.getCar().getClient().getName());
            card.setDescription(String.format("R$ %.2f", order.getPrice()));
            card.registerObserver(this);
            baseCards.add(card);
        }

        paginationList = new PaginationList<>(baseCards, perPage);
        updatePage(0);
    }

    @FXML
    public void clearFilter() {
        filterClient.setValue(null);
        filterCar.setValue(null);
        filterDateStart.clear();
        filterDateEnd.clear();

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
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/orderForm.fxml");
            loader.setController(new OrderForm(screenManager, new Order(), service));
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
            Order order = service.getTreatmentById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/orderForm.fxml");
            loader.setController(new OrderForm(screenManager, order, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}