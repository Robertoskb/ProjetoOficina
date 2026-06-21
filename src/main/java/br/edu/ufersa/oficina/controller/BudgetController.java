package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardAdd;
import br.edu.ufersa.oficina.components.CardGeneric;
import br.edu.ufersa.oficina.components.CardTreatment;
import br.edu.ufersa.oficina.controller.form.BudgetForm;
import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.BudgetService;
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

public class BudgetController extends TreatmentController<Budget, BudgetService> {

    @FXML private ComboBox<Client> filterClient;
    @FXML private ComboBox<Car> filterCar;
    @FXML private TextField filterDateStart;
    @FXML private TextField filterDateEnd;

    private final ClientService clientService = new ClientService();
    private final CarService carService = new CarService();

    public BudgetController(ScreenManager screenManager) {
        super(screenManager, new BudgetService());
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

        for (Budget budget : service.getAllTreatments()) {
            CardTreatment card = new CardTreatment();
            if (budget.isFinish()) card.removeButton(card.getBtnCheck());
            card.setCardId(budget.getId());
            card.setTitle(budget.getCar().getModel() + " de " + budget.getCar().getClient().getName());
            card.setDescription(String.format("R$ %.2f", budget.getPrice()));
            card.registerObserver(this);

            baseCards.add(card);
        }
    }

    @FXML
    public void filter() {
        try {
            Client client = filterClient.getValue();
            Car car = filterCar.getValue();
            String dataInicioStr = filterDateStart.getText() != null ? filterDateStart.getText().trim() : "";
            String dataFimStr = filterDateEnd.getText() != null ? filterDateEnd.getText().trim() : "";
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate dataInicio = !dataInicioStr.isEmpty() ? LocalDate.parse(dataInicioStr, fmt) : null;
            LocalDate dataFim = !dataFimStr.isEmpty() ? LocalDate.parse(dataFimStr, fmt) : null;

            ArrayList<Budget> filtered = new ArrayList<>();
            for (Budget budget : service.getAllTreatments()) {
                boolean matchClient = client == null || budget.getCar().getClient().getId() == client.getId();
                boolean matchCar = car == null || budget.getCar().getId() == car.getId();
                boolean matchDate = true;

                if (dataInicio != null || dataFim != null) {
                    LocalDate budgetDate = budget.getDate_start();
                    if (budgetDate == null) {
                        matchDate = false;
                    } else {
                        if (dataInicio != null && budgetDate.isBefore(dataInicio)) matchDate = false;
                        if (dataFim != null && budgetDate.isAfter(dataFim)) matchDate = false;
                    }
                }

                if (matchClient && matchCar && matchDate) {
                    filtered.add(budget);
                }
            }

            ArrayList<CardGeneric> baseCards = ((PaginatorController) this).cards;
            baseCards.clear();

            CardAdd cardAdd = new CardAdd();
            cardAdd.registerObserver(this);
            baseCards.add(cardAdd);

            for (Budget budget : filtered) {
                CardTreatment card = new CardTreatment();
                if (budget.isFinish()) card.removeButton(card.getBtnCheck());
                card.setCardId(budget.getId());
                card.setTitle(budget.getCar().getModel() + " de " + budget.getCar().getClient().getName());
                card.setDescription(String.format("R$ %.2f", budget.getPrice()));
                card.registerObserver(this);

                baseCards.add(card);
            }

            paginationList = new PaginationList<>(baseCards, perPage);
            updatePage(0);

        } catch (Exception e) {
            alert("Verifique se as datas estão no formato dd/MM/yyyy. Erro: " + e.getMessage());
        }
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
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/budgetForm.fxml");
            loader.setController(new BudgetForm(screenManager, new Budget(), service));
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
            Budget budget = service.getTreatmentById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/budgetForm.fxml");
            loader.setController(new BudgetForm(screenManager, budget, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}