package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.PartForm;
import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Services.PartsService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PartController extends PaginatorController<PartsService> {

    public PartController(ScreenManager screenManager) {
        super(screenManager, new PartsService());
    }

    @Override
    public void generateCards() throws IOException {
        for (Part part : service.getAllParts()) {
            CardSubject card = new CardSubject();
            card.setCardId(part.getId());
            card.setTitle(part.getName());
            card.setDescription(part.getManufacturer() + " - " + part.getModel());
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/partForm.fxml");
            loader.setController(new PartForm(screenManager, service));
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
            Part part = service.getPartById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/partForm.fxml");
            loader.setController(new PartForm(screenManager, part, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}