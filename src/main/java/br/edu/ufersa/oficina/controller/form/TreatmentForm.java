package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.*;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.PartsService;
import br.edu.ufersa.oficina.model.Services.ServiceService;
import br.edu.ufersa.oficina.model.Services.TreatmentService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public abstract class TreatmentForm<T extends Treatment, S extends TreatmentService<T>> extends Form<T , S>{
    @FXML protected ComboBox<Car> carComboBox;

    @FXML protected ComboBox<Part> partComboBox;

    @FXML protected ComboBox<Service> serviceComboBox;

    @FXML protected TableView<Part> partTable;

    @FXML protected TableColumn<Part, String> partNameColumn;

    @FXML protected TableColumn<Part, Double> partPriceColumn;

    @FXML protected TableColumn<Part, Void> partActionColumn;

    @FXML protected TableView<Service> serviceTable;

    @FXML protected TableColumn<Service, String> serviceNameColumn;

    @FXML protected TableColumn<Service, Double> servicePriceColumn;

    @FXML protected TableColumn<Service, Void> serviceActionColumn;

    @FXML protected DatePicker startDatePicker;

    @FXML protected DatePicker finishDatePicker;

    @FXML private Spinner<Integer> discountSpinner;

    @FXML protected Label totalLabel;

    @FXML protected CheckBox paidCheckBox;

    @FXML protected Button saveButton;

    protected final ObservableList<Part> selectedParts =
            FXCollections.observableArrayList();

    protected final ObservableList<Service> selectedServices =
            FXCollections.observableArrayList();

    protected final CarService carService = new CarService();
    protected final PartsService partsService = new PartsService();
    protected final ServiceService serviceService = new ServiceService();

    public TreatmentForm(ScreenManager screenManager, T entity, S service, String lastFxml) {
        super(screenManager, entity, service, lastFxml);
    }

    public TreatmentForm(ScreenManager screenManager, S service, String lastFxml) {
        super(screenManager, service, lastFxml);
    }

    @Override
    public void initialize(){
        try {
            carComboBox.getItems().setAll(carService.getAllCars());

            partComboBox.getItems().setAll(partsService.getAllParts());
            serviceComboBox.getItems().setAll(serviceService.getAllServices());

            partTable.setItems(selectedParts);
            serviceTable.setItems(selectedServices);

            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            servicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            discountSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)
            );

            discountSpinner.valueProperty().addListener(
                    (obs, oldValue, newValue) -> updateTotal()
            );

            super.initialize();

        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void fill(){
        carComboBox.setValue(entity.getCar());

        selectedParts.addAll(entity.getParts());
        selectedServices.addAll(entity.getServices());

        for (Part p1: entity.getParts())
            partComboBox.getItems().removeIf(p2 -> p1.getId() == p2.getId());
        for (Service s1: entity.getServices())
            serviceComboBox.getItems().removeIf(s2 -> s1.getId() == s2.getId());

        startDatePicker.setValue(entity.getDate_start());
        finishDatePicker.setValue(entity.getDate_finish());

        totalLabel.setText(
                String.format("R$ %.2f", entity.getPrice())
        );
    }


    @Override
    public void setEntityValues() {

        entity.setCar(carComboBox.getValue());

        entity.setParts(new ArrayList<>(selectedParts));

        entity.setServices(new ArrayList<>(selectedServices));

        entity.setDate_start(startDatePicker.getValue());

        entity.setDate_finish(finishDatePicker.getValue());

        double total = selectedParts.stream()
                        .mapToDouble(Part::getPrice)
                        .sum()
                        +
                        selectedServices.stream()
                                .mapToDouble(Service::getPrice)
                                .sum();

        entity.setPrice(total);
    }

    @FXML private void addPart() {
        Part part = partComboBox.getValue();

        if (part != null) {
            selectedParts.add(part);
            partComboBox.getItems().remove(part);
            partComboBox.setValue(null);
            updateTotal();
        }
    }

    @FXML public void addService(){
        Service serviceEntity = serviceComboBox.getValue();

        if (serviceEntity != null){
            selectedServices.add(serviceEntity);
            serviceComboBox.getItems().remove(serviceEntity);
            serviceComboBox.setValue(null);
            updateTotal();
        }
    }

    private void updateTotal() {

        double total = 0;

        total += selectedParts.stream()
                .mapToDouble(Part::getPrice)
                .sum();

        total += selectedServices.stream()
                .mapToDouble(Service::getPrice)
                .sum();

        total *= (1 - discountSpinner.getValue()/100.0);

        totalLabel.setText(
                String.format("R$ %.2f", total)
        );
    }
}
