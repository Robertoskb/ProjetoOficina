package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.scene.control.Alert;

public abstract class BaseController {
    protected final ScreenManager screenManager = ScreenManager.getInstance();

    public void success(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso!");
        alert.setHeaderText(message);

        alert.showAndWait();
    }

    public void error(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        alert.setHeaderText(message);

        alert.showAndWait();
    }

}
