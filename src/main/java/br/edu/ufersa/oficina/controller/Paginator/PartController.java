package br.edu.ufersa.oficina.controller.Paginator;

import br.edu.ufersa.oficina.components.CardEntity;
import br.edu.ufersa.oficina.controller.form.PartForm;
import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Services.PartService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PartController extends PaginatorController<Part, PartService> {

    @FXML private ComboBox<String> filterManufacturer;
    @FXML private ComboBox<String> filterModel;
    @FXML private ComboBox<String> filterName;

    public PartController(PartService partService) {
        super(partService);
        entities = service.getAllParts();
    }

    @Override
    public void initialize() throws IOException {
        Set<String> manufacturers = new HashSet<>();
        Set<String> models = new HashSet<>();
        Set<String> names = new HashSet<>();

        if (entities != null) {
            for (Part p : entities) {
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
        for (Part entityPart : entities) {
            CardEntity card = new CardEntity();
            card.setCardId(entityPart.getId());
            card.setTitle(entityPart.getName() + " | " + entityPart.getModel());
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
                entities = service.getPartsByManufacturer(manufacturer.trim());
                loadPagination();
            } catch (Exception e) {
                error(e.getMessage());
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
                entities = service.getPartsByModel(model.trim());
                loadPagination();
            } catch (Exception e) {
                error(e.getMessage());
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
                entities = service.getPartsByName(name.trim());
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
        filterManufacturer.setValue(null);
        filterModel.setValue(null);
        filterName.setValue(null);

        try {
            entities = service.getAllParts();
            loadPagination();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/partForm.fxml");
            loader.setController(new PartForm(new Part(), service));
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
            Part p = service.getPartById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/partForm.fxml");
            loader.setController(new PartForm(p, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }
}