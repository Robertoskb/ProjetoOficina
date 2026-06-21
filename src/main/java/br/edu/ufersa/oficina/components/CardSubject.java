package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.controller.Observer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class CardSubject extends Card implements Subject {

    protected ArrayList<Observer> observers = new ArrayList<>();

    public CardSubject() throws IOException {
        super("entityCard.fxml");
    }

    public CardSubject(String fxml) throws IOException {
        super(fxml);
    }

    private boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Remoção");
        alert.setHeaderText("Deseja realmente excluir este item?");

        Optional<ButtonType> dialog = alert.showAndWait();

        return dialog.isPresent() && dialog.get() == ButtonType.OK;
    }

    public void edit(){
        notifyEdit(cardId);
    }

    public void delete(){
        if (confirmDelete()){
            notifyDelete(cardId);
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
    public void notifyAdd() {

    }

    @Override
    public void notifyDelete(int id){
        for (Observer observer: observers)
            observer.delete(id);
    }

    @Override
    public void notifyEdit(int id) {
        for (Observer observer: observers)
            observer.edit(id);
    }
}
