package br.edu.ufersa.oficina.ui;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.controller.LoginController;
import br.edu.ufersa.oficina.model.Services.UserService;

public class ControllerFactory {
    private final UserService userService;

    public ControllerFactory(){
        userService = new UserService();
    }

    public Object create(Class<?> clazz){
        if (clazz == LoginController.class)
            return new LoginController(userService);

        throw new MecNotFoundException("Controller não encontrado");
    }
}
