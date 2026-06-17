package br.edu.ufersa.oficina.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenLoader {
    private final ControllerFactory factory;

    public ScreenLoader(ScreenManager screenManager){
        this.factory = new ControllerFactory(screenManager);
    }

    public Parent load(String fxml) throws IOException {
        String basePath = "/br/edu/ufersa/oficina/view/";
        Logger.getLogger(FXMLLoader.class.getName()).setLevel(Level.OFF);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(basePath + fxml));

        loader.setControllerFactory(factory::create);

        return loader.load();
    }
}
