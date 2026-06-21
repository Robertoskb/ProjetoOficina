package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.PartForm;
import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Services.PartsService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PartController extends PaginatorController<PartsService> {

    @FXML private ComboBox<String> filterManufacturer;
    @FXML private ComboBox<String> filterModel;
    @FXML private ComboBox<String> filterName;

    protected ArrayList<Part> currentParts;

    public PartController(ScreenManager screenManager) {
        super(screenManager, new PartsService());
        currentParts = service.getAllParts();
    }

    @Override
    public void initialize() throws IOException {
        Set<String> manufacturers = new HashSet<>();
        Set<String> models = new HashSet<>();
        Set<String> names = new HashSet<>();

        if (currentParts != null) {
            for (Part p : currentParts) {
                if (p.getManufacturer() != null && !p.getManufacturer().isEmpty()) {
                    manufacturers.add(p.getManufacturer());
                }
                if (p.getModel() != null && !p.getModel().isEmpty()) {
                    models.add(p.getModel());
                }
                if (p.getName() != null && !p.getName().isEmpty()) {
                    names.add(p.getName());
                }
            }
        }

        filterManufacturer.getItems().addAll(manufacturers);
        filterModel.getItems().addAll(models);
        filterName.getItems().addAll(names);

        super.initialize();
    }

    @Override
    public void generateCards() throws IOException {
        for (Part entityPart : currentParts) {
            CardSubject card = new CardSubject();
            card.setCardId(entityPart.getId());
            card.setTitle(entityPart.getName());
            card.setDescription(String.format("R$ %.2f", entityPart.getPrice()));
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @FXML
    public void filterByManufacturer() {
        String manufacturer = filterManufacturer.getValue();
        if (manufacturer != null && !manufacturer.trim().isEmpty()) {
            try {
                currentParts = service.getPartsByManufacturer(manufacturer.trim());
                loadPagination();
            } catch (Exception e) {
                alert(e.getMessage());
            }
        } else {
            clearFilter();
        }
    }

    @FXML
    public void filterByModel() {
        String model = filterModel.getValue();
        if (model != null && !model.trim().isEmpty()) {
            try {
                currentParts = service.getPartsByModel(model.trim());
                loadPagination();
            } catch (Exception e) {
                alert(e.getMessage());
            }
        } else {
            clearFilter();
        }
    }

    @FXML
    public void filterByName() {
        String name = filterName.getValue();
        if (name != null && !name.trim().isEmpty()) {
            try {
                currentParts = service.getPartsByName(name.trim());
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
        filterManufacturer.setValue(null);
        filterModel.setValue(null);
        filterName.setValue(null);

        try {
            currentParts = service.getAllParts();
            loadPagination();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/partForm.fxml");
            loader.setController(new PartForm(screenManager, new Part(), service));
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
            Part p = service.getPartById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/partForm.fxml");
            loader.setController(new PartForm(screenManager, p, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}