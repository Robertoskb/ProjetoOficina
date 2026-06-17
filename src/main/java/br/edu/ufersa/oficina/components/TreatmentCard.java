package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.Entity.Treatment;
import br.edu.ufersa.oficina.model.Services.TreatmentService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

public class TreatmentCard<T extends Treatment, S extends TreatmentService<T>> extends Card<S> {
    @FXML private Button btnCheck;

    public TreatmentCard() throws IOException {
        super("treatmentCard.fxml");
    }

    public void setBtnCheckText(String text){
        btnCheck.setText(text);
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
            if (service != null) {
                try {
                    service.finish(entityId);
                    notifyObservers(entityId);
                }

                catch (MecNotFoundException ignore){}
            }
    }

}
