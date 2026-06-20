package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.ServiceForm;
import br.edu.ufersa.oficina.model.Entity.Service;
import br.edu.ufersa.oficina.model.Services.ServiceService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ServiceController extends PaginatorController<ServiceService> {

    public ServiceController(ScreenManager screenManager) {
        super(screenManager, new ServiceService());
    }

    @Override
    public void generateCards() throws IOException {
        for (Service entityService : service.getAllServices()) {
            CardSubject card = new CardSubject();
            card.setCardId(entityService.getId());
            card.setTitle(entityService.getName());
            card.setDescription("R$ " + entityService.getPrice());
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/serviceForm.fxml");
            loader.setController(new ServiceForm(screenManager, new Service("", 0), service));
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
            Service s = service.getServiceById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/serviceForm.fxml");
            loader.setController(new ServiceForm(screenManager, s, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}