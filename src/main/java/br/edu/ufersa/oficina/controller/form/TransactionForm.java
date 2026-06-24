package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.*;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.PartService;
import br.edu.ufersa.oficina.model.Services.ServiceService;
import br.edu.ufersa.oficina.model.Services.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class TransactionForm<T extends Transaction, S extends TransactionService<T, ?>> extends Form<T , S>{
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

    protected CarService carService;
    protected PartService partService;
    protected ServiceService serviceService;

    public TransactionForm(T entity, S service, String lastFxml, CarService carService, PartService partService, ServiceService serviceService) {
        super(entity, service, lastFxml);
        setCarService(carService);
        setPartService(partService);
        setServiceService(serviceService);
    }

    @Override
    public void initialize(){
        try {
            carComboBox.getItems().setAll(carService.getAllCars());

            partComboBox.getItems().setAll(partService.getAllParts());
            serviceComboBox.getItems().setAll(serviceService.getAllServices());

            partTable.setItems(selectedParts);
            serviceTable.setItems(selectedServices);

            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            servicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            configurePartActionColumn();
            configureServiceActionColumn();

            discountSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)
            );

            discountSpinner.valueProperty().addListener(
                    (obs, oldValue, newValue) -> updateTotal()
            );

            startDatePicker.setValue(LocalDate.now());

            super.initialize();

        } catch (Exception e) {
            error(e.getMessage());
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

        double total = getTotal();

        if (entity.getPrice() < total)
            discountSpinner.increment((int) Math.round(100 * (1.0 - entity.getPrice()/total)));

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

        double total = getTotal();

        entity.setPrice(total);
    }

    private double getTotal() {
        double total = selectedParts.stream()
                        .mapToDouble(Part::getPrice)
                        .sum()
                        +
                        selectedServices.stream()
                                .mapToDouble(Service::getPrice)
                                .sum();

        total *= (1 - discountSpinner.getValue()/100.0);

        return total;
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

    @FXML private void addService(){
        Service serviceEntity = serviceComboBox.getValue();

        if (serviceEntity != null){
            selectedServices.add(serviceEntity);
            serviceComboBox.getItems().remove(serviceEntity);
            serviceComboBox.setValue(null);
            updateTotal();
        }
    }

    private void removePart(Part part) {
        selectedParts.remove(part);
        partComboBox.getItems().add(part);
        updateTotal();
    }

    private void removeService(Service service) {
        selectedServices.remove(service);
        serviceComboBox.getItems().add(service);
        updateTotal();
    }

    private void updateTotal() {

        double total = getTotal();

        totalLabel.setText(
                String.format("R$ %.2f", total)
        );
    }

    private void configurePartActionColumn() {

        partActionColumn.setCellFactory(param -> new TableCell<>() {

            private final Button removeButton =
                    new Button("Remover");

            {
                removeButton.setOnAction(event ->
                        removePart(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : removeButton);
            }
        });
    }

    private void configureServiceActionColumn() {

        serviceActionColumn.setCellFactory(param -> new TableCell<>() {

            private final Button removeButton =
                    new Button("Remover");

            {
                removeButton.setOnAction(event ->
                        removeService(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : removeButton);
            }
        });
    }

    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    public void setCarService(CarService carService) {
        this.carService = carService;
    }
}
