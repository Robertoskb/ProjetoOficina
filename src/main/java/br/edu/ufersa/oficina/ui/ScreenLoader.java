package br.edu.ufersa.oficina.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ScreenLoader {
    public Parent load(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(new ControllerFactory()::create);

        return loader.load();
    }
}
