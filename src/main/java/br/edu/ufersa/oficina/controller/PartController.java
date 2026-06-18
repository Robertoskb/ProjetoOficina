package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardService;
import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Services.PartsService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class PartController extends PaginatorController<PartsService>{
    public PartController(ScreenManager screenManager){
        super(screenManager, new PartsService());
    }

    @Override
    public void generateCards() throws IOException {
        for (Part part: service.getAllParts()){
            CardService<PartsService> card = new CardService<>();
            card.setService(service);
            card.setCardId(part.getId());
            card.setTitle(part.getName());
            card.setDescription("R$ " + part.getPrice());
            card.registerObserver(this);

            cards.add(card);
        }
    }
}
