package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardService;
import br.edu.ufersa.oficina.model.Entity.User;
import br.edu.ufersa.oficina.model.Services.UserService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class UserController extends PaginatorController<UserService>{
    public UserController(ScreenManager screenManager){
        super(screenManager, new UserService());
    }

    @Override
    public void generateCards() throws IOException {
        for (User user: service.getAllUsers()){
            CardService<UserService> card = new CardService<>();
            card.setService(service);
            card.setCardId(user.getId());

            String name = user.getName();
            name += user.isAdmin() ? " 👑" : "";

            card.setTitle(name);
            card.setDescription(user.getEmail());

            if (user.getId() == screenManager.getUser().getId())
                card.removeButton(card.getBtnDelete());

            card.registerObserver(this);

            cards.add(card);

        }
    }
}
