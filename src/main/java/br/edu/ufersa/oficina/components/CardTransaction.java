package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.controller.Paginator.TransactionObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class CardTransaction extends CardEntity implements TransactionSubject {
    @FXML private Button btnCheck;

    private final ArrayList<TransactionObserver> observers = new ArrayList<>();

    public CardTransaction() throws IOException {
        super("transactionCard.fxml");
    }

    private boolean confirmFinish() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Finalização");
        alert.setHeaderText("Deseja realmente finalizar esse atendimento?");

        Optional<ButtonType> dialog = alert.showAndWait();

        return dialog.isPresent() && dialog.get() == ButtonType.OK;
    }

    public void finish(){
        if (confirmFinish())
            notifyFinish(cardId);
    }

    @Override
    public void registerObserver(TransactionObserver observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(TransactionObserver observer){
        observers.remove(observer);
    }

    @Override
    public void notifyFinish(int id) {
        for (TransactionObserver observer: observers)
            observer.finish(id);
    }

    @Override
    public void notifyAdd() {
        for (TransactionObserver observer: observers)
            observer.add();
    }

    @Override
    public void notifyEdit(int id){
        for (TransactionObserver observer: observers)
            observer.edit(id);
    }

    @Override
    public void notifyDelete(int id){
        for (TransactionObserver observer: observers)
            observer.delete(id);
    }

    public Button getBtnCheck(){
        return btnCheck;
    }
}
