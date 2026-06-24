package br.edu.ufersa.oficina.controller.Paginator;

import br.edu.ufersa.oficina.components.CardEntity;
import br.edu.ufersa.oficina.controller.form.ServiceForm;
import br.edu.ufersa.oficina.model.Entity.Service;
import br.edu.ufersa.oficina.model.Services.ServiceService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class ServiceController extends PaginatorController<Service, ServiceService> {

    @FXML private TextField filterName;

    public ServiceController(ServiceService serviceService) {
        super(serviceService);
        entities = service.getAllServices();
    }

    @Override
    public void generateCards() throws IOException {
        for (Service entityService : entities) {
            CardEntity card = new CardEntity();
            card.setCardId(entityService.getId());
            card.setTitle(entityService.getName());
            card.setDescription(String.format("R$ %.2f", entityService.getPrice()));
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @FXML
    public void filterByName() {
        String name = filterName.getText();
        if (name != null && !name.trim().isEmpty()) {
            try {
                entities = service.getServiceByName(name.trim());
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
        filterName.clear();

        try {
            entities = service.getAllServices();
            loadPagination();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.loader("form/serviceForm.fxml");
            loader.setController(new ServiceForm(new Service("", 0), service));
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
            Service s = service.getServiceById(id);
            FXMLLoader loader = screenManager.loader("form/serviceForm.fxml");
            loader.setController(new ServiceForm(s, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }
}