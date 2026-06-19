package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.controller.BaseController;
import br.edu.ufersa.oficina.model.Entity.Entity;
import br.edu.ufersa.oficina.model.Services.GenericService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.scene.Parent;

import java.io.IOException;

public abstract class Form<E extends Entity, S extends GenericService<E>> extends BaseController {
    protected S service;
    protected E entity;

    public Form(ScreenManager screenManager, E entity, S service) {
        super(screenManager);
        setEntity(entity);
        setService(service);
    }

    public Form(ScreenManager screenManager, S service){
        super(screenManager);
        setService(service);
    }

    public void initialize(){
        if (entity.isValid())
            fill();
    }

    public abstract void fill();
    public abstract void setEntityValues();
    public abstract void save() throws IOException;

    public boolean isSaved() throws IOException {
        boolean saved;
        if (entity.isValid())
            saved = update(entity);
        else
            saved = create(entity);

        return saved;
    };

    public boolean create(E entity){
        try {
            service.insert(entity);

            return true;

        } catch (Exception e) {
            alert(e.getMessage());

            return false;
        }
    }

    public boolean update(E entity){
        try {
            service.update(entity);

            return true;
        } catch (Exception e) {
            alert(e.getMessage());

            return false;
        }
    }

    public void leave(String fxml){
        try {
            Parent view = screenManager.getScreenLoader().load(fxml);

            screenManager.setCenter(view);
        }

        catch (Exception e){
            alert(e.getMessage());
        }
    }

    public S getService() {
        return service;
    }

    public void setService(S service) {
        this.service = service;
    }

    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }
}
