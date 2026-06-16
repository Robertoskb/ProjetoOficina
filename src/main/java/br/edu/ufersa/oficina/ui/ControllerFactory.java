package br.edu.ufersa.oficina.ui;

import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.controller.ClientController;
import br.edu.ufersa.oficina.controller.LoginController;
import br.edu.ufersa.oficina.controller.MainController;
import br.edu.ufersa.oficina.controller.MenuController;
import br.edu.ufersa.oficina.model.Services.UserService;

public class ControllerFactory {
    private final UserService userService = new UserService();


    private final ScreenManager screenManager;

    public ControllerFactory(ScreenManager screenManager){
        this.screenManager = screenManager;
    }

    public Object create(Class<?> clazz){
        if (clazz == LoginController.class)
            return new LoginController(userService, screenManager);
        if (clazz == MainController.class)
            return new MainController(screenManager);
        if (clazz == MenuController.class)
            return new MenuController(screenManager);
        if (clazz == ClientController.class)
            return new ClientController(screenManager);

        throw new MecNotFoundException("Controller não encontrado");
    }
}
