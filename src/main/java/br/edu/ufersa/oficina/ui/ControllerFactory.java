package br.edu.ufersa.oficina.ui;

import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.controller.*;
import br.edu.ufersa.oficina.model.Services.UserService;

public class ControllerFactory {
    private final UserService userService = new UserService();


    private final ScreenManager screenManager;

    public ControllerFactory(ScreenManager screenManager){
        this.screenManager = screenManager;
    }

    public Object create(Class<?> clazz) {
        if (clazz == LoginController.class)
            return new LoginController(userService, screenManager);
        if (clazz == MainController.class)
            return new MainController(screenManager);
        if (clazz == MenuController.class)
            return new MenuController(screenManager);
        if (PaginatorController.class.isAssignableFrom(clazz)){
            try {
                return clazz.getDeclaredConstructor(screenManager.getClass()).newInstance(screenManager);
            }
            catch (Exception e){
                throw new MecNotFoundException("Controller não encontrado");
            }
        }

        throw new MecNotFoundException("Controller não encontrado");
    }
}
