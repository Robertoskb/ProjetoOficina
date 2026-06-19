package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.utils.Observer;
import br.edu.ufersa.oficina.utils.TreatmentObserver;
import br.edu.ufersa.oficina.utils.TreatmentSubject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class CardTreatment extends CardSubject implements TreatmentSubject {
    @FXML private Button btnCheck;

    private final ArrayList<TreatmentObserver> observers = new ArrayList<>();

    public CardTreatment() throws IOException {
        super("treatmentCard.fxml");
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
    public void registerObserver(TreatmentObserver observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(TreatmentObserver observer){
        observers.remove(observer);
    }

    @Override
    public void notifyFinish(int id) {
        for (TreatmentObserver observer: observers)
            observer.finish(id);
    }

    @Override
    public void notifyAdd() {
        for (TreatmentObserver observer: observers)
            observer.add();
    }

    @Override
    public void notifyEdit(int id){
        for (TreatmentObserver observer: observers)
            observer.edit(id);
    }

    public Button getBtnCheck(){
        return btnCheck;
    }
}
