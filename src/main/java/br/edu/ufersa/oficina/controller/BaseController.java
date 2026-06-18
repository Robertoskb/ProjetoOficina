package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.scene.control.Alert;

public abstract class BaseController {
    protected final ScreenManager screenManager;

    public BaseController(ScreenManager screenManager){
        this.screenManager = screenManager;
    }

    public void alert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        alert.setHeaderText(message);

        alert.showAndWait();
    }
}
