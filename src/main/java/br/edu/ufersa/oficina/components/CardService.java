package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.Services.GenericService;
import br.edu.ufersa.oficina.utils.Observer;
import br.edu.ufersa.oficina.utils.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class CardService<S extends GenericService<?>> extends Card implements Subject {
    protected S service;
    protected int entityId;

    protected ArrayList<Observer> observers = new ArrayList<>();

    public CardService() throws IOException {
        super("entityCard.fxml");
    }

    public CardService(String fxml) throws IOException {
        super(fxml);
    }

    public void setService(S service) {
        this.service = service;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }


    private boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Remoção");
        alert.setHeaderText("Deseja realmente excluir este item?");

        Optional<ButtonType> dialog = alert.showAndWait();

        return dialog.isPresent() && dialog.get() == ButtonType.OK;
    }
    public void delete(){
        if (confirmDelete()){

            if (service != null)
                try {
                    service.delete(entityId);
                    notifyObservers(entityId);
                }
                catch (MecNotFoundException ignore){}
        }
    }

    @Override
    public void registerObserver(Observer observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(int id){
        for (Observer observer: observers)
            observer.update(id);
    }
}
