package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

public abstract class BaseController {
    protected final ScreenManager screenManager = ScreenManager.getInstance();
    protected BaseController parentController;

    public void success(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso!");
        alert.setHeaderText(message);

        alert.showAndWait();
    }

    public void alert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        alert.setHeaderText(message);

        alert.showAndWait();
    }

    public void setParentController(BaseController controller){
        this.parentController = controller;
    }
}
