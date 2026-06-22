package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.controller.BaseController;
import br.edu.ufersa.oficina.model.Entity.Entity;
import br.edu.ufersa.oficina.model.Services.GenericService;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import java.io.IOException;

public abstract class Form<E extends Entity, S extends GenericService<E>> extends BaseController {
    protected S service;
    protected E entity;
    protected String lastFxml;

    public Form(E entity, S service, String lastFxml) {
        setEntity(entity);
        setService(service);
        setLastFxml(lastFxml);
    }

    public Form(S service, String lastFxml){
        setService(service);
        setLastFxml(lastFxml);
    }

    public void initialize(){
        if (entity != null && entity.isValid())
            fill();
    }

    public abstract void fill();
    public abstract void setEntityValues();

    @FXML public void save() throws IOException {
        try {
            setEntityValues();
        }

        catch (Exception e){
            error(e.getMessage());

            return;
        }
        if (isSaved()){
            leave(lastFxml);
        }
    }


    @FXML public void cancel(){
        leave(lastFxml);
    }


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
            error(e.getMessage());

            return false;
        }
    }

    public boolean update(E entity){
        try {
            service.update(entity);

            return true;
        } catch (Exception e) {
            error(e.getMessage());

            return false;
        }
    }

    public void leave(String fxml){
        try {
            Parent view = screenManager.getScreenLoader().load(fxml);

            screenManager.setCenter(view);
        }

        catch (Exception e){
            error(e.getMessage());
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

    public String getLastFxml() {
        return lastFxml;
    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }
}
