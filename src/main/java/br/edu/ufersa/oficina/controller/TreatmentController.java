package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardGeneric;
import br.edu.ufersa.oficina.components.CardTreatment;
import br.edu.ufersa.oficina.controller.form.TreatmentForm;
import br.edu.ufersa.oficina.controller.form.UserForm;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Entity.Treatment;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.model.Services.TreatmentService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.TreatmentObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class TreatmentController<T extends Treatment, S extends TreatmentService<T>> extends PaginatorController<S> implements TreatmentObserver {
    protected ArrayList<CardTreatment> cards = new ArrayList<>();

    @FXML protected ComboBox<Client> filterClient;
    @FXML protected ComboBox<Car> filterCar;
    @FXML protected DatePicker filterDateStart;
    @FXML protected DatePicker filterDateEnd;

    protected final ClientService clientService = new ClientService();
    protected final CarService carService = new CarService();

    protected ArrayList<T> treatments;

    public TreatmentController(ScreenManager screenManager, S service){
        super(screenManager, service);
        treatments = service.getAllTreatments();
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
        for (T treatment: treatments){
            CardTreatment card = new CardTreatment();

            if (treatment.isFinish())
                card.removeButton(card.getBtnCheck());

            card.setCardId(treatment.getId());

            Car car = treatment.getCar();
            Client client = car.getClient();

            String first = car.getModel() != null ? car.getModel(): "<Carro Removido>";
            String last = client.getName()!= null ? client.getName() : "<Cliente Removido>";
            String plate = car.getPlate() != null ? car.getPlate(): "";


            card.setTitle(first + " de " + last + " " + plate);
            card.setDescription("R$ " + String.format("R$ %.2f", treatment.getPrice()));
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
                treatments = service.getTreatmentByPeriod(start, end);

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
                treatments = service.getTreatmentByCar(filterCar.getValue());

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
                treatments = service.getTreatmentByClient(filterClient.getValue());

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
            for (CardTreatment card: cards)
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

        treatments = service.getAllTreatments();

        loadPagination();
    }

}
