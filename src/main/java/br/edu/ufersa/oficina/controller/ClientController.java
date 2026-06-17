package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardService;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import java.io.IOException;


public class ClientController extends PaginatorController<ClientService> {
    public ClientController(ScreenManager screenManager){
        super(screenManager, new ClientService());
    }

    @Override
    public void generateCards() throws IOException {
        for (Client client: service.getAllClients()){
            CardService<ClientService> card = new CardService<>();
            card.setService(service);
            card.setEntityId(client.getId());
            card.setTitle(client.getName());
            card.setDescription(client.getAddress());
            card.registerObserver(this);

            cards.add(card);
        }
    }
}
