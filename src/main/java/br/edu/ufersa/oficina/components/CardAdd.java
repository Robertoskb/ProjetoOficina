package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.controller.form.Form;
import br.edu.ufersa.oficina.model.Entity.Entity;
import br.edu.ufersa.oficina.model.Services.GenericService;
import br.edu.ufersa.oficina.utils.Observer;
import br.edu.ufersa.oficina.utils.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;

public class CardAdd extends CardGeneric implements Subject {
    @FXML private Button btnAdd;

    private final ArrayList<Observer> observers = new ArrayList<>();

    public CardAdd() throws IOException{
        super("addCard.fxml");
    }

    public void add(){
        notifyAdd();
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
    public void notifyAdd() {
        for (Observer observer: observers)
            observer.add();
    }

    @Override
    public void notifyDelete(int id){}
    @Override
    public void notifyEdit(int id) {}
}
