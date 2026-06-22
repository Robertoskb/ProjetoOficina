package br.edu.ufersa.oficina.controller.Paginator;

import br.edu.ufersa.oficina.components.CardEntity;
import br.edu.ufersa.oficina.controller.form.ClientForm;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.ClientService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ClientController extends PaginatorController<Client, ClientService> {

    public ClientController(ClientService clientService) {
        super(clientService);
    }

    @Override
    public void generateCards() throws IOException {
        for (Client client : service.getAllClients()) {
            CardEntity card = new CardEntity();
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
            loader.setController(new ClientForm(new Client(), service));
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
            Client client = service.getClientById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/clientForm.fxml");
            loader.setController(new ClientForm(client, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }
}