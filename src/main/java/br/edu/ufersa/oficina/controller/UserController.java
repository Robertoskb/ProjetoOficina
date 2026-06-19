package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.UserForm;
import br.edu.ufersa.oficina.model.Entity.User;
import br.edu.ufersa.oficina.model.Services.UserService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class UserController extends PaginatorController<UserService>{
    public UserController(ScreenManager screenManager){
        super(screenManager, new UserService());
    }

    @Override
    public void generateCards() throws IOException {
        for (User user: service.getAllUsers()){
            CardSubject card = new CardSubject();
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

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/userForm.fxml");

            loader.setController(new UserForm(screenManager, new User(), service));

            Parent view = loader.load();

            screenManager.setCenter(view);

            screenManager.show();
        }
        catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void edit(int id) {
        try {
            User user = service.getUserById(id);

            FXMLLoader loader = screenManager.getScreenLoader().loader("form/userForm.fxml");

            loader.setController(new UserForm(screenManager, user, service));

            Parent view = loader.load();

            screenManager.setCenter(view);

            screenManager.show();
        }
        catch (Exception e) {
            alert(e.getMessage());
        }
    }
}
