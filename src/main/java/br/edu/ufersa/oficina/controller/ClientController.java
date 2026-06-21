package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.ClientForm;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ClientController extends PaginatorController<ClientService> {

    public ClientController(ScreenManager screenManager) {
        super(screenManager, new ClientService());
    }

    @Override
    public void generateCards() throws IOException {
        for (Client client : service.getAllClients()) {
            CardSubject card = new CardSubject();
            card.setCardId(client.getId());
            card.setTitle(client.getName());
            card.setDescription(client.getAddress());
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/clientForm.fxml");
            loader.setController(new ClientForm(screenManager, new Client(), service));
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
            Client client = service.getClientById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/clientForm.fxml");
            loader.setController(new ClientForm(screenManager, client, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}