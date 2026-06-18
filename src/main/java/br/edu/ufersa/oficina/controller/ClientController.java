package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.model.Entity.Client;
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
            CardSubject card = new CardSubject();
            card.setCardId(client.getId());
            card.setTitle(client.getName());
            card.setDescription(client.getAddress());
            card.registerObserver(this);

            cards.add(card);
        }
    }
}
