package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.model.Entity.Service;
import br.edu.ufersa.oficina.model.Services.ServiceService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class ServiceController extends PaginatorController<ServiceService>{
    public ServiceController(ScreenManager screenManager){
        super(screenManager, new ServiceService());
    }

    @Override
    public void generateCards() throws IOException {
        for (Service entityService: service.getAllServices()){
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

    }

    @Override
    public void edit(int id) {

    }
}
