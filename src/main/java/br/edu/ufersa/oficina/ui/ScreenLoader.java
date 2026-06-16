package br.edu.ufersa.oficina.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ScreenLoader {
    private final ControllerFactory factory;

    public ScreenLoader(ScreenManager screenManager){
        this.factory = new ControllerFactory(screenManager);
    }

    public Parent load(String fxml) throws IOException {
        String basePath = "/br/edu/ufersa/oficina/view/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(basePath + fxml));

        loader.setControllerFactory(factory::create);

        return loader.load();
    }
}
