package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.controller.BaseController;
import br.edu.ufersa.oficina.model.Services.GenericService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.scene.control.Alert;

public abstract class Form<E, S extends GenericService<E>> extends BaseController {
    protected S service;

    public Form(ScreenManager screenManager) {
        super(screenManager);
    }

    public void save(){};



    public void create(E entity){
        try {
            service.insert(entity);
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    public void update(E entity){
        try {
            service.update(entity);
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}
