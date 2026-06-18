package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.User;
import br.edu.ufersa.oficina.model.Services.UserService;
import br.edu.ufersa.oficina.ui.ScreenManager;

public class UserForm extends Form<User, UserService>{

    public UserForm(ScreenManager screenManager) {
        super(screenManager);
    }
}
