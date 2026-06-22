package br.edu.ufersa.oficina.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenLoader {
    private final ControllerFactory factory;


    public ScreenLoader(){
        this.factory = new ControllerFactory();
    }

    public FXMLLoader loader(String fxml){
        String basePath = "/br/edu/ufersa/oficina/view/";
        Logger.getLogger(FXMLLoader.class.getName()).setLevel(Level.OFF);

        return new FXMLLoader(getClass().getResource(basePath + fxml));
    }

    public Parent load(String fxml) throws IOException {
        FXMLLoader loader = loader(fxml);

        loader.setControllerFactory(factory::create);

        return loader.load();
    }

    public ControllerFactory getFactory() {
        return factory;
    }
}
