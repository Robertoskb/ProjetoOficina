package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardTransaction;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Entity.Transaction;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.model.Services.TransactionService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class TransactionController<T extends Transaction, S extends TransactionService<T,?>> extends PaginatorController<S> implements TransactionObserver {
    protected ArrayList<CardTransaction> cards = new ArrayList<>();

    @FXML protected ComboBox<Client> filterClient;
    @FXML protected ComboBox<Car> filterCar;
    @FXML protected DatePicker filterDateStart;
    @FXML protected DatePicker filterDateEnd;

    protected final ClientService clientService = new ClientService();
    protected final CarService carService = new CarService();

    protected ArrayList<T> transactions;

    public TransactionController(ScreenManager screenManager, S service){
        super(screenManager, service);
        transactions = service.getAllTransactions();
    }

    @Override
    public void initialize() throws IOException{
        super.initialize();

        filterDateStart.setValue(LocalDate.now().plusMonths(-1));
        filterDateEnd.setValue(LocalDate.now());

        filterClient.getItems().addAll(clientService.getAllClients());
        filterCar.getItems().addAll(carService.getAllCars());
    }

    @Override
    public void generateCards() throws IOException {
        for (T transaction: transactions){
            CardTransaction card = new CardTransaction();

            if (transaction.isFinish())
                card.removeButton(card.getBtnCheck());

            card.setCardId(transaction.getId());

            Car car = transaction.getCar();
            Client client = car.getClient();

            if (!car.isValid() || !client.isValid()){
                card.removeButton(card.getBtnCheck());
                card.removeButton(card.getBtnEdit());
            }


            String first = car.getModel() != null ? car.getModel(): "<Carro Removido>";
            String last = client.getName()!= null ? client.getName() : "<Cliente Removido>";
            String plate = car.getPlate() != null ? car.getPlate(): "";


            card.setTitle(first + " de " + last + " " + plate);
            card.setDescription("R$ " + String.format("R$ %.2f", transaction.getPrice()));
            card.registerObserver(this);

            super.cards.add(card);
            cards.add(card);
        }
    }

    @FXML public void periodFilter() throws IOException {
        LocalDate start = filterDateStart.getValue();
        LocalDate end = filterDateEnd.getValue();

        if (start != null && end != null) {
            try {
                transactions = service.getTransactionByPeriod(start, end);

                loadPagination();
            }

            catch (Exception e){
                alert(e.getMessage());
            }
        }

    }
    @FXML public void carFilter() throws IOException {
        if (filterCar.getValue() != null) {
            try {
                transactions = service.getTransactionByCar(filterCar.getValue());

                loadPagination();
            }

            catch (Exception e){
                alert(e.getMessage());
            }
        }
    }
    @FXML public void clientFilter() throws IOException {
        if (filterClient.getValue() != null){
            try {
                transactions = service.getTransactionByClient(filterClient.getValue());

                loadPagination();
            }

            catch (Exception e){
                alert(e.getMessage());
            }
        }
    }

    @Override
    public void finish(int id) {
        try {
            service.finish(id);
            for (CardTransaction card: cards)
                if (card.getCardId() == id)
                    card.removeButton(card.getBtnCheck());
            updatePage(pagination.getCurrentPageIndex());
        }

        catch (Exception e){
            alert(e.getMessage());
        }
    }

    @FXML public void clearFilter() throws IOException {
        filterClient.setValue(null);
        filterCar.setValue(null);
        filterDateStart.setValue(LocalDate.now());
        filterDateEnd.setValue(LocalDate.now());

        transactions = service.getAllTransactions();

        loadPagination();
    }

}
